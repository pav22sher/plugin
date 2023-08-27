package com.example.plugin.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Editor;
import icons.Icons;
import org.jetbrains.annotations.NotNull;

public class CustomDefaultActionGroup extends DefaultActionGroup {
  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    //в фоновом потоке (BGT)
    return ActionUpdateThread.BGT;
  }

  @Override
  public void update(AnActionEvent event) {
    Editor editor = event.getData(CommonDataKeys.EDITOR);
    event.getPresentation().setEnabled(editor != null);
    event.getPresentation().setIcon(Icons.Sdk_default_icon);
  }
}
