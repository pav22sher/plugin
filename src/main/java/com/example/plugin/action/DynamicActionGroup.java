package com.example.plugin.action;

import com.example.plugin.psi.PsiNavigationDemoAction;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import icons.Icons;
import org.jetbrains.annotations.NotNull;

public class DynamicActionGroup extends ActionGroup {
  @Override
  public AnAction @NotNull [] getChildren(AnActionEvent e) {
    return new AnAction[]{
            new PopupDialogAction("Action Added at Runtime", "Dynamic Action Demo", Icons.Shit_icon),
            new PsiNavigationDemoAction("Psi Demo", "Psi Demo", Icons.Shit_icon)
    };
  }
}
