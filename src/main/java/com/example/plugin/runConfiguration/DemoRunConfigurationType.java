package com.example.plugin.runConfiguration;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NotNullLazyValue;

public class DemoRunConfigurationType extends ConfigurationTypeBase {

  static final String ID = "DemoRunConfiguration";

  protected DemoRunConfigurationType() {
    super(ID, "Demo", "Demo run configuration type", NotNullLazyValue.createValue(() -> AllIcons.Nodes.Console));
    addFactory(new DemoConfigurationFactory(this));
  }

}
