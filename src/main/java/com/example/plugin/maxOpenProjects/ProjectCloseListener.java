package com.example.plugin.maxOpenProjects;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

public class ProjectCloseListener implements ProjectManagerListener {

  @Override
  public void projectClosed(@NotNull Project project) {
    if (ApplicationManager.getApplication().isUnitTestMode()) {
      return;
    }
    ProjectCountingService projectCountingService = ApplicationManager.getApplication().getService(ProjectCountingService.class);
    projectCountingService.decreaseOpenProjectCount();
  }

}
