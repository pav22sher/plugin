package com.example.plugin.facet;

import org.jetbrains.annotations.NotNull;

public class DemoFacetState {

  static final String DEMO_FACET_INIT_PATH = "";

  public String myPathToSdk;

  DemoFacetState() {
    setDemoFacetState(DEMO_FACET_INIT_PATH);
  }

  @NotNull
  public String getDemoFacetState() {
    return myPathToSdk;
  }

  public void setDemoFacetState(@NotNull String newPath) {
    myPathToSdk = newPath;
  }

}
