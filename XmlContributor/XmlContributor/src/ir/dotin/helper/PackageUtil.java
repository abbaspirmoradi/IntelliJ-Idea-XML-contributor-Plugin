package ir.dotin.helper;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import ir.dotin.ClassIcon;
import ir.dotin.PackageIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class PackageUtil {

  static PsiPackage[] givePsiSubPackagesInPackage(String attributeText, Project project) {
    PsiPackage psiPackage = JavaPsiFacade.getInstance(project).findPackage(attributeText);
    if (psiPackage != null)
      return psiPackage.getSubPackages();
    return new PsiPackage[0];
  }

  static List<LookupElement> givePackageLookupElements(PsiPackage[] packages) {
    List<LookupElement> lookupElements = new ArrayList<>();
    for (PsiPackage aPackage : packages) {
      lookupElements.add(LookupElementBuilder.create(Objects.requireNonNull(aPackage.getName()) + Constants.DOT).withIcon(PackageIcon.FILE));
    }
    return lookupElements;
  }

  static boolean isPackage(String attributeText, Project project) {
    return JavaPsiFacade.getInstance(project).findPackage(attributeText) != null;
  }

  static String getPackageNameFromAttributeValueString(String attributeText) {
    if (attributeText.contains(Constants.DOT)) {
      return attributeText.substring(attributeText.lastIndexOf(Constants.DOT) + 1);
    } else return "";
  }

  static List<LookupElement> giveRootLookupElements(PsiElement element) {
    List<LookupElement> lookupElements = new ArrayList<>();
    Module currentModule = ModuleUtil.findModuleForPsiElement(element);
    if (currentModule != null) {
      VirtualFile[] roots = ModuleRootManager.getInstance(currentModule).getSourceRoots();
      if (roots.length != 0) {
        VirtualFile[] files = roots[0].getChildren();
        for (VirtualFile file : files) {
          if (file.isDirectory()) {
            if (!file.getName().equals(Constants.TEST_PACKAGE))
              lookupElements.add(LookupElementBuilder.create(file.getName()).withIcon(PackageIcon.FILE));
          } else if (file.getExtension() != null && file.getExtension().equals(Constants.JAVA_EXTENSION)) {
            lookupElements.add(LookupElementBuilder.create(file.getNameWithoutExtension() + Constants.DOT).withIcon(ClassIcon.FILE));
          }

        }
      }
    }

    return lookupElements;
  }
}
