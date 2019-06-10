package ir.dotin.conf;

import com.intellij.openapi.project.Project;
import ir.dotin.helper.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ir.dotin.helper.ProjectUtil.getCurrentActiveProject;


public class Configuration {

  public static List<String> getXmlAttributes() {
    List<String> targetXmlAttributesFromConfigFile = getXmlAttributesFromConfigFile();
    List<String> targetXmlAttributesFromUserConfig = getXmlAttributesFromUserConfig();
    targetXmlAttributesFromConfigFile.addAll(targetXmlAttributesFromUserConfig);
    return targetXmlAttributesFromConfigFile;
  }

  private static List<String> getXmlAttributesFromUserConfig() {
    Project currentActiveProject = getCurrentActiveProject();
    if (currentActiveProject != null) {
      try {
        XmlContributorConfiguration xmlContributorConfiguration = XmlContributorConfiguration.getInstance(currentActiveProject);
        String xmlAttributes = xmlContributorConfiguration != null ? xmlContributorConfiguration.getTargetXmlAttributes() : null;
        if (xmlAttributes != null && !xmlAttributes.isEmpty()) {

          List<String> targetXmlAttributes = Arrays.asList(xmlAttributes.split(Constants.COMMA));
          return targetXmlAttributes.stream().map(String::trim).collect(Collectors.toList());
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return new ArrayList<>();
  }

  private static List<String> getXmlAttributesFromConfigFile() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.CONFIGURATION_FILE_NAME_WITHOUT_EXTENSION);
    List<String> targetXmlAttributes = Arrays.asList(resourceBundle.getString(Constants.TARGET_XML_ATTRIBUTES).split(Constants.COMMA));
    return targetXmlAttributes.stream().map(String::trim).collect(Collectors.toList());
  }

}
