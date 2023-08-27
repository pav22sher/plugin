package com.example.plugin.runConfiguration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DemoConfigurationFactory extends ConfigurationFactory {

  protected DemoConfigurationFactory(ConfigurationType type) {
    super(type);
  }

  @Override
  public @NotNull String getId() {
    return DemoRunConfigurationType.ID;
  }

  @NotNull
  @Override
  public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
    return new DemoRunConfiguration(project, this, "Demo");
  }

  @Nullable
  @Override
  public Class<? extends BaseState> getOptionsClass() {
    return DemoRunConfigurationOptions.class;
  }

}
