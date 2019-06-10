package ir.dotin.helper;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import ir.dotin.MethodIcon;

import java.util.*;

import static com.intellij.codeInsight.completion.CompletionUtilCore.DUMMY_IDENTIFIER;
import static ir.dotin.helper.ClassUtil.*;
import static ir.dotin.helper.MethodUtil.*;
import static ir.dotin.helper.PackageUtil.*;

public class BaseUtil {

  public static List<PsiElement> getPsiElements(Project project, String attributeText) {

    List<PsiElement> psiElements = null;
    GlobalSearchScope scope = GlobalSearchScope.allScope(project);
    if (attributeText.contains(Constants.DOT)) {
      String packageFullQualifiedName = getPackageFullQualifiedName(attributeText);

      if (isPackage(packageFullQualifiedName, project)) {
        PsiPackage aPackage = JavaPsiFacade.getInstance(project).findPackage(packageFullQualifiedName);
        String desirePackageName = getPackageNameFromAttributeValueString(attributeText);
        if ((packageFullQualifiedName).equals(attributeText)) {

          if (aPackage != null && Objects.equals(aPackage.getName(), desirePackageName)) {

            if (psiElements == null) {
              psiElements = new ArrayList<>();
            }
            psiElements.add(aPackage);
          }
        }


      } else if (isClass(attributeText, project, scope)) {
        PsiClass aClass = JavaPsiFacade.getInstance(project).findClass(attributeText, scope);
        String desireClassName = getClassNameFromAttributeValueString(attributeText);
        if (aClass != null && Objects.equals(aClass.getName(), desireClassName)) {

          if (psiElements == null) {
            psiElements = new ArrayList<>();
          }
          psiElements.add(aClass);
        }
      }

      String desireMethodNameWithParameters = getCompleteMethodNameWithParametersFromAttributeValueString(attributeText);

      if (isMethod(attributeText)) {
        String classFullQualifiedName = getFullQualifiedNameOfClassFromAttributeValueString(attributeText);
        PsiClass clazz = JavaPsiFacade.getInstance(project).findClass(classFullQualifiedName, scope);
        if (clazz != null) {
          if ((classFullQualifiedName + Constants.DOT + desireMethodNameWithParameters).equals(attributeText)) {
            PsiMethod[] psiMethods = PsiTreeUtil.getChildrenOfType(clazz.getOriginalElement(), PsiMethod.class);
            if (psiMethods != null) {
              for (PsiMethod method : psiMethods) {
                String methodFullName = getMethodNameAndParametersStringByMethodName(method);
                if (methodFullName.equals(desireMethodNameWithParameters)) {
                  if (psiElements == null) {
                    psiElements = new ArrayList<>();
                  }
                  psiElements.add(method);
                }
              }
            }

          }
        }
      }
    }

    return psiElements != null ? psiElements : Collections.emptyList();
  }

  public static List<LookupElement> getPsiElements(PsiElement element, String attributeText) {

    int indexOfCurrentPosition = attributeText.indexOf(DUMMY_IDENTIFIER);
    attributeText = attributeText.substring(0, indexOfCurrentPosition);

    Project project = element.getProject();
    GlobalSearchScope scope = GlobalSearchScope.allScope(project);

    if (attributeText.contains(Constants.DOT)) {

      String packageFullQualifiedName = getPackageFullQualifiedNameWithoutDot(attributeText);

      if (isPackage(packageFullQualifiedName, project)) {
        PsiClass[] classes = givePsiClassesInPackage(packageFullQualifiedName, project);
        PsiPackage[] packages = givePsiSubPackagesInPackage(packageFullQualifiedName, project);
        List<LookupElement> classVariants = giveClassLookupElements(Arrays.asList(classes));
        List<LookupElement> packageVariants = givePackageLookupElements(packages);
        classVariants.addAll(packageVariants);
        return classVariants;
      }

      List<LookupElement> variants = new ArrayList<>();
      String classFullQualifiedName = getFullQualifiedNameOfClassFromAttributeValueString(attributeText);
      PsiClass clazz = JavaPsiFacade.getInstance(project).findClass(classFullQualifiedName, scope);
      if (clazz != null) {
        PsiMethod[] psiMethods = PsiTreeUtil.getChildrenOfType(clazz.getOriginalElement(), PsiMethod.class);
        if (psiMethods != null) {
          for (PsiMethod method : psiMethods) {
            String completedParameter = getMethodNameAndParametersStringByMethodName(method);
            variants.add(LookupElementBuilder.create(completedParameter).withIcon(MethodIcon.FILE).withTypeText(method.getContainingFile().getName()));
          }
        }
        return variants;
      }

      //suggest all roots
    } else {
      return giveRootLookupElements(element);
    }
    return new ArrayList<>();

  }


  private static String getPackageFullQualifiedNameWithoutDot(String attributeText) {
    return attributeText.substring(0, attributeText.lastIndexOf(Constants.DOT));
  }


  private static String getPackageFullQualifiedName(String attributeText) {
    int lastIndexOfDot = attributeText.lastIndexOf(Constants.DOT);
    if (lastIndexOfDot == attributeText.length())
      return attributeText.substring(0, attributeText.lastIndexOf(Constants.DOT) - 1);
    else return attributeText;
  }

}
