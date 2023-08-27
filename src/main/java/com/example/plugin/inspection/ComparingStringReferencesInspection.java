package com.example.plugin.inspection;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.NotNull;

public class ComparingStringReferencesInspection extends AbstractBaseJavaLocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitBinaryExpression(@NotNull PsiBinaryExpression expression) {
                super.visitBinaryExpression(expression);
                IElementType opSign = expression.getOperationTokenType();
                if (opSign == JavaTokenType.EQEQ || opSign == JavaTokenType.NE) {
                    PsiExpression lOperand = expression.getLOperand();
                    PsiExpression rOperand = expression.getROperand();
                    if (rOperand == null || isNullLiteral(lOperand) || isNullLiteral(rOperand)) {
                        return;
                    }
                    if (isStringType(lOperand) || isStringType(rOperand)) {
                        holder.registerProblem(
                                expression,
                                InspectionBundle.message("inspection.comparing.string.references.problem.descriptor"),
                                new ReplaceWithEqualsQuickFix()
                        );
                    }
                }
            }

            private boolean isStringType(PsiExpression operand) {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(operand.getType());
                if (psiClass == null) {
                    return false;
                }
                return "java.lang.String".equals(psiClass.getQualifiedName());
            }

            private static boolean isNullLiteral(PsiExpression expression) {
                return expression instanceof PsiLiteralExpression && ((PsiLiteralExpression) expression).getValue() == null;
            }
        };
    }

    private static class ReplaceWithEqualsQuickFix implements LocalQuickFix {
        @NotNull
        @Override
        public String getName() {
            return InspectionBundle.message("inspection.comparing.string.references.use.quickfix");
        }

        @NotNull
        public String getFamilyName() {
            return getName();
        }

        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiBinaryExpression binaryExpression = (PsiBinaryExpression) descriptor.getPsiElement();
            IElementType opSign = binaryExpression.getOperationTokenType();
            PsiExpression lExpr = binaryExpression.getLOperand();
            PsiExpression rExpr = binaryExpression.getROperand();
            if (rExpr == null) {
                return;
            }

            PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
            PsiMethodCallExpression equalsCall = (PsiMethodCallExpression) factory.createExpressionFromText("a.equals(b)", null);
            PsiExpression qualifierExpression = equalsCall.getMethodExpression().getQualifierExpression();
            assert qualifierExpression != null;
            qualifierExpression.replace(lExpr);
            equalsCall.getArgumentList().getExpressions()[0].replace(rExpr);
            PsiExpression result = (PsiExpression) binaryExpression.replace(equalsCall);

            if (opSign == JavaTokenType.NE) {
                PsiPrefixExpression negation = (PsiPrefixExpression) factory.createExpressionFromText("!a", null);
                PsiExpression operand = negation.getOperand();
                assert operand != null;
                operand.replace(result);
                result.replace(negation);
            }
        }
    }

}
