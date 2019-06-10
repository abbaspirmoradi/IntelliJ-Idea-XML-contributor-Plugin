package ir.dotin.conf;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "xmlAttributes",
        storages = @Storage("targetXmlAttributes.xml"))
public class XmlContributorConfiguration implements PersistentStateComponent<XmlContributorConfiguration> {

  @Nullable
  @Override
  public XmlContributorConfiguration getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull XmlContributorConfiguration xmlContributorConfiguration) {
    XmlSerializerUtil.copyBean(xmlContributorConfiguration,this);
  }

  @Nullable
  public static XmlContributorConfiguration getInstance(Project project) {
    return ServiceManager.getService(project, XmlContributorConfiguration.class);
  }

  String targetXmlAttributes;

  public String getTargetXmlAttributes() {
    return targetXmlAttributes;
  }

  public void setTargetXmlAttributes(String targetXmlAttributes) {
    this.targetXmlAttributes = targetXmlAttributes;
  }

}

