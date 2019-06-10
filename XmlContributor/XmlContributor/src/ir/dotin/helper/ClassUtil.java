package ir.dotin.helper;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import ir.dotin.ClassIcon;

import java.util.ArrayList;
import java.util.List;

class ClassUtil {

  static String getFullQualifiedNameOfClassFromAttributeValueString(String attributeText) {
    if (attributeText.contains(Constants.OPEN_PARENTHESIS)) {
      return attributeText.substring(0, attributeText.substring(0, attributeText.indexOf(Constants.OPEN_PARENTHESIS)).lastIndexOf(Constants.DOT));
    } else if (attributeText.contains(Constants.DOT)) {
      return attributeText.substring(0, attributeText.lastIndexOf(Constants.DOT));
    } else return "";
  }

  static PsiClass[] givePsiClassesInPackage(String attributeText, Project project) {
    PsiPackage psiPackage = JavaPsiFacade.getInstance(project).findPackage(attributeText);
    if (psiPackage != null) {
      return psiPackage.getClasses();
    }
    return new PsiClass[0];
  }

  static List<LookupElement> giveClassLookupElements(List<PsiClass> classes) {
    List<LookupElement> lookupElements = new ArrayList<>();
    for (PsiClass aClass : classes) {
      lookupElements.add(LookupElementBuilder.create(aClass.getName()+Constants.DOT).withIcon(ClassIcon.FILE).withTypeText(aClass.getContainingFile().getName()));
    }
    return lookupElements;
  }

  static String getClassNameFromAttributeValueString(String attributeText) {
    if (attributeText.contains(Constants.DOT)) {
      return attributeText.substring(attributeText.lastIndexOf(Constants.DOT) + 1);
    } else return "";
  }

  static boolean isClass(String attributeText, Project project, GlobalSearchScope scope) {
    return JavaPsiFacade.getInstance(project).findClass(attributeText, scope) != null;
  }
}

