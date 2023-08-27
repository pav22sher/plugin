package com.example.plugin.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.openapi.module.Module;

public class DemoFacet extends Facet<DemoFacetConfiguration> {

  public DemoFacet(FacetType facetType,
                   Module module,
                   String name,
                   DemoFacetConfiguration configuration,
                   Facet underlyingFacet) {
    super(facetType, module, name, configuration, underlyingFacet);
  }

}
