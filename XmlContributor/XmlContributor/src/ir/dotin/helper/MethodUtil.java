package ir.dotin.helper;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import ir.dotin.exception.UnHandledMethodParameterType;

public class MethodUtil {

  static String getMethodNameAndParametersStringByMethodName(PsiMethod method) {

    PsiParameter[] psiParameterList = method.getParameterList().getParameters();
    StringBuilder completionParameters = new StringBuilder(method.getName() + Constants.OPEN_PARENTHESIS);
    for (PsiParameter parameter : psiParameterList) {
      completionParameters.append(getQualifiedNameOfTypeByParameter(parameter));
      completionParameters.append(Constants.COMMA);
    }
    if (completionParameters.toString().contains(Constants.COMMA)) {
      completionParameters.setLength(Math.max(completionParameters.length() - 1, 0));
    }
    completionParameters.append(Constants.CLOSE_PARENTHESIS);
    return completionParameters.toString();
  }


  static String getCompleteMethodNameWithParametersFromAttributeValueString(String key) {
    if (!key.contains(Constants.OPEN_PARENTHESIS)) {
      return "";
    } else if (!key.contains(Constants.CLOSE_PARENTHESIS)) {
      return "";
    } else {
      return key.substring(key.substring(0, key.indexOf(Constants.OPEN_PARENTHESIS)).lastIndexOf(Constants.DOT) + 1, key.indexOf(Constants.CLOSE_PARENTHESIS) + 1);
    }
  }

  private static String getQualifiedNameOfTypeByParameter(PsiParameter parameter) {
    if (parameter.getType() instanceof PsiPrimitiveType) {
      return parameter.getType().getCanonicalText();
    } else if (parameter.getType() instanceof PsiArrayType) {
      return ((parameter.getType())).getCanonicalText();
    } else if (parameter.getType() instanceof PsiClassType) {
      if (PsiTypesUtil.getPsiClass((parameter.getType())) != null) {
        return PsiTypesUtil.getPsiClass((parameter.getType())).getQualifiedName();
      } else {
        throw new UnHandledMethodParameterType("Parameter with name of (" + parameter.getName() + ") has not valid class type!");
      }
    } else {
      throw new UnHandledMethodParameterType("Parameter with name of (" + parameter.getName() + ") has not valid type!");
    }
  }

  static boolean isMethod(String attributeText) {
    return !getCompleteMethodNameWithParametersFromAttributeValueString(attributeText).isEmpty();
  }
}