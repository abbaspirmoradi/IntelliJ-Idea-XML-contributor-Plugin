package ir.dotin.contributor;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.*;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import ir.dotin.conf.Configuration;
import ir.dotin.helper.Constants;
import ir.dotin.model.PsiElementReference;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class XmlReferencesContributor extends PsiReferenceContributor {

  @Override
  public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {

    //load all attributes that you want participate into this plugin abilities
    List<String> targetXmlAttributes = Configuration.getXmlAttributes();

    for (String targetXmlAttribute : targetXmlAttributes) {
      psiReferenceRegistrar.registerReferenceProvider(XmlPatterns.xmlAttributeValue()
              .withParent(XmlPatterns.xmlAttribute().withName(targetXmlAttribute)), new PsiReferenceProvider() {
        @NotNull
        @Override
        public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                     @NotNull ProcessingContext
                                                             context) {
          String value = ((XmlAttributeValue) element).getValue();
          if (!value.isEmpty()) {
            int startIndex;
            startIndex = value.contains(Constants.DOT) ? value.contains(Constants.OPEN_PARENTHESIS) ? value.substring(0, value.indexOf(Constants.OPEN_PARENTHESIS)).lastIndexOf(Constants.DOT) + 2 : value.lastIndexOf(Constants.DOT) + 2 : 1;
            return new PsiReference[]{new PsiElementReference(element, new TextRange(startIndex, value.length() + 1), value)
            };
          }

          return PsiReference.EMPTY_ARRAY;
        }
      });

    }
  }
}





