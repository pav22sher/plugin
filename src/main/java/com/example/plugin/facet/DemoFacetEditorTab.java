package com.example.plugin.facet;

import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class DemoFacetEditorTab extends FacetEditorTab {
  private static final String FACET_PANEL_PROMPT = "Path To SDK: ";

  private final DemoFacetState mySettings;
  private final JTextField myPath;

  public DemoFacetEditorTab(@NotNull DemoFacetState state, @NotNull FacetEditorContext context,
                            @NotNull FacetValidatorsManager validator) {
    mySettings = state;
    myPath = new JTextField(state.getDemoFacetState());
  }

  @NotNull
  @Override
  public JComponent createComponent() {
    JPanel top = new JPanel(new BorderLayout());
    top.add(new JLabel(FACET_PANEL_PROMPT), BorderLayout.WEST);
    top.add(myPath);
    JPanel facetPanel = new JPanel(new BorderLayout());
    facetPanel.add(top, BorderLayout.NORTH);
    return facetPanel;
  }

  @Override
  @Nls(capitalization = Nls.Capitalization.Title)
  public String getDisplayName() {
    return DemoFacetType.FACET_NAME;
  }

  @Override
  public boolean isModified() {
    return !StringUtil.equals(mySettings.getDemoFacetState(), myPath.getText().trim());
  }

  @Override
  public void apply() throws ConfigurationException {
    try {
      String newTextContent = myPath.getText();
      mySettings.setDemoFacetState(newTextContent);
    } catch(Exception e) {
      throw new ConfigurationException(e.toString());
    }
  }

  @Override
  public void reset() {
    myPath.setText(mySettings.getDemoFacetState());
  }

}
