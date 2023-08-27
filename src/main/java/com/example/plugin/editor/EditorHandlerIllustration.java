package com.example.plugin.editor;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class EditorHandlerIllustration extends AnAction {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        EditorActionHandler actionHandler = EditorActionManager.getInstance().getActionHandler(IdeActions.ACTION_EDITOR_CLONE_CARET_BELOW);
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        actionHandler.execute(editor, primaryCaret, e.getDataContext());
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        boolean menuAllowed = false;
        if (editor != null && project != null) {
            menuAllowed = !editor.getCaretModel().getAllCarets().isEmpty();
        }
        e.getPresentation().setEnabledAndVisible(menuAllowed);
    }

}
