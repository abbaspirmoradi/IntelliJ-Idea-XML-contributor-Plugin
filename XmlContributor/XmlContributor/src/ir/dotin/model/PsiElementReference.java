package ir.dotin.model;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import ir.dotin.helper.BaseUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PsiElementReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
  private String xmlAttributeValue;

  public PsiElementReference(@NotNull PsiElement element, TextRange textRange, String value) {
    super(element, textRange);
    xmlAttributeValue = value;
  }

  @NotNull
  @Override
  public ResolveResult[] multiResolve(boolean incompleteCode) {
    Project project = myElement.getProject();
    final List<PsiElement> psiElements = BaseUtil.getPsiElements(project, xmlAttributeValue);
    List<ResolveResult> results = new ArrayList<>();
    for (PsiElement property : psiElements) {
      results.add(new PsiElementResolveResult(property));
    }
    return results.toArray(new ResolveResult[results.size()]);
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    ResolveResult[] resolveResults = multiResolve(false);
    return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return BaseUtil.getPsiElements(myElement, xmlAttributeValue).toArray();
  }
}

