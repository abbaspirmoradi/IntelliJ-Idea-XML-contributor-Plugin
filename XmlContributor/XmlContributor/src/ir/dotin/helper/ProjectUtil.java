package ir.dotin.helper;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.IdeFrameImpl;

import java.awt.*;

public class ProjectUtil {

  private final static com.intellij.openapi.diagnostic.Logger LOGGER = Logger.getInstance(PackageUtil.class);
  public static Project getCurrentActiveProject() {
    Project[] projects = ProjectManager.getInstance().getOpenProjects();
    for (Project project : projects) {
      LOGGER.info("projectName:"+project.getName());
      Window window = WindowManager.getInstance().suggestParentWindow(project);
      if (window != null&&window.isActive()) {
        LOGGER.info("windowType:"+window.getType());
        LOGGER.info("windowName:"+window.getName());
        return ((IdeFrameImpl) window).getProject();
      }
    }
    return null;
  }
}
