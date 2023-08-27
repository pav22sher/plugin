package com.example.plugin.editor;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

class MyTypedHandler extends TypedHandlerDelegate {

  @NotNull
  @Override
  public Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
    Document document = editor.getDocument();
    Runnable runnable = () -> document.insertString(0, "editor_basics\n");
    WriteCommandAction.runWriteCommandAction(project, runnable);
    return Result.STOP;
  }

}
