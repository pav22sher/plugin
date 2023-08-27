package com.example.plugin.facet;

import com.intellij.facet.FacetConfiguration;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DemoFacetConfiguration implements FacetConfiguration, PersistentStateComponent<DemoFacetState> {
  private DemoFacetState myFacetState = new DemoFacetState();

  @Nullable
  @Override
  public DemoFacetState getState() {
    return myFacetState;
  }

  @Override
  public void loadState(@NotNull DemoFacetState state) {
    myFacetState = state;
  }

  @Override
  public FacetEditorTab[] createEditorTabs(FacetEditorContext context, FacetValidatorsManager manager) {
    return new FacetEditorTab[]{
            new DemoFacetEditorTab(myFacetState, context, manager)
    };
  }

}
