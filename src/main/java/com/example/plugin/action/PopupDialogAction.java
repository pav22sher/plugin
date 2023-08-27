package com.example.plugin.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PopupDialogAction extends AnAction {
  public PopupDialogAction() {
    super();
  }
  public PopupDialogAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
    super(text, description, icon);
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    //в фоновом потоке (BGT)
    return ActionUpdateThread.BGT;
  }

  @Override
  public void update(AnActionEvent e) {
    Project project = e.getProject();
    Presentation presentation = e.getPresentation();
    presentation.setEnabledAndVisible(project != null);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    Project currentProject = event.getProject();
    StringBuilder dlgMsg = new StringBuilder(event.getPresentation().getText() + " Selected!");
    String dialogTitle = event.getPresentation().getDescription();
    Navigatable navigatable = event.getData(CommonDataKeys.NAVIGATABLE);
    if (navigatable != null) {
      dlgMsg.append(String.format("\nSelected Element: %s", navigatable));
    }
    Messages.showMessageDialog(
            currentProject,
            dlgMsg.toString(),
            dialogTitle,
            Messages.getInformationIcon()
    );
  }

}
