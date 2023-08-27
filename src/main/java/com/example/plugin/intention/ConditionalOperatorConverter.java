package com.example.plugin.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NonNls
public class ConditionalOperatorConverter extends PsiElementBaseIntentionAction implements IntentionAction {
    @NotNull
    public String getText() {
        return "SDK Convert ternary operator to if statement";
    }

    @NotNull
    public String getFamilyName() {
        return "ConditionalOperatorIntention";
    }

    public boolean isAvailable(@NotNull Project project, Editor editor, @Nullable PsiElement element) {
        if (element == null) {
            return false;
        }
        if (element instanceof PsiJavaToken token) {
            if (token.getTokenType() != JavaTokenType.QUEST) {
                return false;
            }
            if (token.getParent() instanceof PsiConditionalExpression conditionalExpression) {
                return conditionalExpression.getThenExpression() != null && conditionalExpression.getElseExpression() != null;
            }
            return false;
        }
        return false;
    }

    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
        CodeStyleManager codeStylist = CodeStyleManager.getInstance(project);
        //a == b ? a : b
        PsiConditionalExpression conditionalExpression = PsiTreeUtil.getParentOfType(element, PsiConditionalExpression.class, false);
        PsiElement originalStatement = PsiTreeUtil.getParentOfType(conditionalExpression, PsiStatement.class, false);
        while (originalStatement instanceof PsiForStatement) {
            originalStatement = PsiTreeUtil.getParentOfType(originalStatement, PsiStatement.class, true);
        }
        if (originalStatement == null) {
            return;
        }
        if (originalStatement instanceof PsiDeclarationStatement declaration) {
            final PsiElement[] declaredElements = declaration.getDeclaredElements();
            PsiLocalVariable variable = null;
            for (PsiElement declaredElement : declaredElements) {
                if (declaredElement instanceof PsiLocalVariable &&
                        PsiTreeUtil.isAncestor(declaredElement, conditionalExpression, true)) {
                    variable = (PsiLocalVariable) declaredElement;
                    break;
                }
            }
            if (variable == null) {
                return;
            }
            variable.normalizeDeclaration();
            final Object marker = new Object();
            PsiTreeUtil.mark(conditionalExpression, marker);
            PsiExpressionStatement statement = (PsiExpressionStatement) factory.createStatementFromText(variable.getName() + " = 0;", null);
            statement = (PsiExpressionStatement) codeStylist.reformat(statement);
            ((PsiAssignmentExpression) statement.getExpression()).getRExpression().replace(variable.getInitializer());
            variable.getInitializer().delete();
            final PsiElement variableParent = variable.getParent();
            originalStatement = variableParent.getParent().addAfter(statement, variableParent);
            conditionalExpression = (PsiConditionalExpression) PsiTreeUtil.releaseMark(originalStatement, marker);
        }
        PsiIfStatement newIfStmt = (PsiIfStatement) factory.createStatementFromText("if (true) {a=b;} else {c=d;}", null);
        newIfStmt = (PsiIfStatement) codeStylist.reformat(newIfStmt);
        newIfStmt.getCondition().replace(conditionalExpression.getCondition().copy());
        PsiAssignmentExpression assignmentExpression = PsiTreeUtil.getParentOfType(conditionalExpression, PsiAssignmentExpression.class, false);
        String exprFrag = assignmentExpression.getLExpression().getText() + assignmentExpression.getOperationSign().getText();
        String thenStr = exprFrag + conditionalExpression.getThenExpression().getText() + ";";
        PsiExpressionStatement thenStmt = (PsiExpressionStatement) factory.createStatementFromText(thenStr, null);
        ((PsiBlockStatement) newIfStmt.getThenBranch()).getCodeBlock().getStatements()[0].replace(thenStmt);
        String elseStr = exprFrag + conditionalExpression.getElseExpression().getText() + ";";
        PsiExpressionStatement elseStmt = (PsiExpressionStatement) factory.createStatementFromText(elseStr, null);
        ((PsiBlockStatement) newIfStmt.getElseBranch()).getCodeBlock().getStatements()[0].replace(elseStmt);
        originalStatement.replace(newIfStmt);
    }

}
