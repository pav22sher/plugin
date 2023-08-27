package com.example.plugin.maxOpenProjects;

import com.intellij.openapi.components.Service;

@Service
public final class ProjectCountingService {

  private final static int MAX_OPEN_PROJECTS_LIMIT = 3;

  private int myOpenProjectCount = 0;

  public void increaseOpenProjectCount() {
    myOpenProjectCount++;
  }

  public void decreaseOpenProjectCount() {
    if (myOpenProjectCount > 0) {
      myOpenProjectCount--;
    }
  }

  public boolean isOpenProjectsLimitExceeded() {
    return myOpenProjectCount > MAX_OPEN_PROJECTS_LIMIT;
  }

}
