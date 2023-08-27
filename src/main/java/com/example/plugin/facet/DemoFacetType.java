package com.example.plugin.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.facet.FacetTypeId;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import icons.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DemoFacetType extends FacetType<DemoFacet, DemoFacetConfiguration> {

  public static final String FACET_ID = "DEMO_FACET_ID";
  public static final String FACET_NAME = "SDK Facet";
  public static final FacetTypeId<DemoFacet> DEMO_FACET_TYPE_ID = new FacetTypeId<>(FACET_ID);

  public DemoFacetType() {
    super(DEMO_FACET_TYPE_ID, FACET_ID, FACET_NAME);
  }

  @Override
  public DemoFacetConfiguration createDefaultConfiguration() {
    return new DemoFacetConfiguration();
  }

  @Override
  public DemoFacet createFacet(@NotNull Module module,
                               String s,
                               @NotNull DemoFacetConfiguration configuration,
                               Facet facet) {
    return new DemoFacet(this, module, s, configuration, facet);
  }

  @Override
  public boolean isSuitableModuleType(final ModuleType type) {
    return true;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return Icons.Sdk_default_icon;
  }

}
