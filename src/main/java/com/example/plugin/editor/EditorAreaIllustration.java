package com.example.plugin.editor;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class EditorAreaIllustration extends AnAction {

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
    CaretModel caretModel = editor.getCaretModel();
    Caret primaryCaret = caretModel.getPrimaryCaret();
    LogicalPosition logicalPos = primaryCaret.getLogicalPosition();
    VisualPosition visualPos = primaryCaret.getVisualPosition();
    int caretOffset = primaryCaret.getOffset();
    String report = logicalPos + "\n" + visualPos + "\n" + "Offset: " + caretOffset;
    Messages.showInfoMessage(report, "Caret Parameters Inside The Editor");
  }

  @Override
  public void update(@NotNull final AnActionEvent e) {
    Project project = e.getProject();
    Editor editor = e.getData(CommonDataKeys.EDITOR);
    e.getPresentation().setEnabledAndVisible(project != null && editor != null);
  }

}
