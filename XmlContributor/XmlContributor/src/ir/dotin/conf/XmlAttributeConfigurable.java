package ir.dotin.conf;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import ir.dotin.ui.XmlContributorGUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class XmlAttributeConfigurable implements Configurable {

  private XmlContributorGUI xmlContributorGUI;

  private final XmlContributorConfiguration xmlContributorConfiguration;

  public XmlAttributeConfigurable(@NotNull Project project) {
    xmlContributorConfiguration = XmlContributorConfiguration.getInstance(project);
  }

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "XML Contributor Plugin";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "preference.XmlAttributeConfigurable";
  }


  @Nullable
  @Override
  public JComponent createComponent() {
    xmlContributorGUI = new XmlContributorGUI();
    return xmlContributorGUI.getRootPanel();
  }

  @Override
  public boolean isModified() {
    if (xmlContributorGUI != null) {
      return xmlContributorGUI.getTextFieldValue() != null;
    }
    return false;
  }

  @Override
  public void apply() {
    String textFieldValue = xmlContributorGUI.getTextFieldValue();
    if (textFieldValue != null) {
      xmlContributorConfiguration.setTargetXmlAttributes(xmlContributorGUI.getTextFieldValue());
    }
  }

  @Override
  public void disposeUIResources() {
    xmlContributorGUI = null;
  }
}
