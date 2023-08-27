package com.example.plugin.maxOpenProjects;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class ProjectOpenStartupActivity implements StartupActivity.DumbAware {

  @Override
  public void runActivity(@NotNull Project project) {
    if (ApplicationManager.getApplication().isUnitTestMode()) {
      return;
    }
    ProjectCountingService projectCountingService = ApplicationManager.getApplication().getService(ProjectCountingService.class);
    projectCountingService.increaseOpenProjectCount();
    if (projectCountingService.isOpenProjectsLimitExceeded()) {
      String title = String.format("Opening Project \"%s\"", project.getName());
      String message = "<br>The number of open projects exceeds the SDK plugin max_opened_projects limit.<br><br>";
      ApplicationManager.getApplication().invokeLater(() ->
              Messages.showMessageDialog(project, message, title, Messages.getInformationIcon())
      );
    }
  }

}
