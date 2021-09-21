package org.zhupanovdm.bsl.tree;

import org.zhupanovdm.bsl.tree.definition.*;
import org.zhupanovdm.bsl.tree.expression.*;
import org.zhupanovdm.bsl.tree.module.Module;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;
import org.zhupanovdm.bsl.tree.statement.*;

import java.util.Collection;

public abstract class BslTreeVisitor {

    protected void scan(BslTree tree) {
        if (tree != null) {
            tree.accept(this);
        }
    }

    protected void scan(Collection<? extends BslTree> trees) {
        if (trees != null) {
            for (BslTree tree : trees) {
                scan(tree);
            }
        }
    }

    public void visitModule(Module module) {
        scan(module.getBody());
    }

    public void visitCallableDefinition(CallableDefinition callableDefinition) {
        scan(callableDefinition.getDirective());
        scan(callableDefinition.getAsync());
        scan(callableDefinition.getIdentifier());
        scan(callableDefinition.getParameters());
        scan(callableDefinition.getExport());
        scan(callableDefinition.getBody());
    }

    public void visitVariablesDefinition(VariablesDefinition variablesDefinition) {
        scan(variablesDefinition.getDirective());
        scan(variablesDefinition.getBody());
    }

    public void visitAssignmentStatement(AssignmentStatement assignmentStatement) {
        scan(assignmentStatement.getTarget());
        scan(assignmentStatement.getExpression());
    }

    public void visitCallStatement(CallStatement callStatement) {
        scan(callStatement.getExpression());
    }

    public void visitIfStatement(IfStatement ifStatement) {
        scan(ifStatement.getCondition());
        scan(ifStatement.getBody());
        scan(ifStatement.getElsIfBranches());
        scan(ifStatement.getElseClause());
    }

    public void visitElsIfBranch(ElsIfBranch elsIfBranch) {
        scan(elsIfBranch.getCondition());
        scan(elsIfBranch.getBody());
    }

    public void visitElseClause(ElseClause elseClause) {
        scan(elseClause.getBody());
    }

    public void visitWhileStatement(WhileStatement whileStatement) {
        scan(whileStatement.getCondition());
        scan(whileStatement.getBody());
    }

    public void visitForStatement(ForStatement forStatement) {
        scan(forStatement.getIdentifier());
        scan(forStatement.getInit());
        scan(forStatement.getCondition());
        scan(forStatement.getBody());
    }

    public void visitForEachStatement(ForEachStatement forEachStatement) {
        scan(forEachStatement.getIdentifier());
        scan(forEachStatement.getCollection());
        scan(forEachStatement.getBody());
    }

    public void visitReturnStatement(ReturnStatement returnStatement) {
        scan(returnStatement.getExpression());
    }

    public void visitContinueStatement(ContinueStatement continueStatement) {
        // nothing to visit for continue statement
    }

    public void visitBreakStatement(BreakStatement breakStatement) {
        // nothing to visit for break statement
    }

    public void visitRaiseStatement(RaiseStatement raiseStatement) {
        scan(raiseStatement.getExpression());
    }

    public void visitEmptyStatement(EmptyStatement emptyStatement) {
        // nothing to visit for empty statement
    }

    public void visitTryStatement(TryStatement tryStatement) {
        scan(tryStatement.getBody());
        scan(tryStatement.getExceptClause());
    }

    public void visitExceptClause(ExceptClause exceptClause) {
        scan(exceptClause.getBody());
    }

    public void visitExecuteStatement(ExecuteStatement executeStatement) {
        scan(executeStatement.getExpression());
    }

    public void visitLabelDefinition(LabelDefinition labelDefinition) {
        scan(labelDefinition.getLabel());
    }

    public void visitGotoStatement(GotoStatement gotoStatement) {
        scan(gotoStatement.getLabel());
    }

    public void visitAddHandlerStatement(AddHandlerStatement addHandlerStatement) {
        scan(addHandlerStatement.getEvent());
        scan(addHandlerStatement.getHandler());
    }

    public void visitRemoveHandlerStatement(RemoveHandlerStatement removeHandlerStatement) {
        scan(removeHandlerStatement.getEvent());
        scan(removeHandlerStatement.getHandler());
    }

    public void visitIdentifier(Identifier identifier) {
        // nothing to visit for identifier
    }

    public void visitVariable(Variable variable) {
        scan(variable.getIdentifier());
        scan(variable.getExport());
    }

    public void visitParameter(Parameter parameter) {
        scan(parameter.getVal());
        scan(parameter.getIdentifier());
        scan(parameter.getDefaultValue());
    }

    public void visitParameterVal(Parameter.Val val) {
        // nothing to visit for parameter val
    }

    public void visitLabel(Label label) {
        scan(label.getIdentifier());
    }

    public void visitCompilationDirective(CompilationDirective compilationDirective) {
        // nothing to visit for compilation directive
    }

    public void visitCallableAsync(CallableDefinition.Async async) {
        // nothing to visit for callable async
    }

    public void visitExport(Export export) {
        // nothing to visit for export
    }

    public void visitPreprocessorIf(PreprocessorIf preprocessorIf) {
        scan(preprocessorIf.getCondition());
        scan(preprocessorIf.getBody());
        scan(preprocessorIf.getElsIfBranches());
    }

    public void visitPreprocessorElsif(PreprocessorElsif preprocessorElsif) {
        scan(preprocessorElsif.getCondition());
        scan(preprocessorElsif.getBody());
    }

    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        scan(binaryExpression.getOperator());
        scan(binaryExpression.getLeft());
        scan(binaryExpression.getRight());
    }

    public void visitBinaryOperator(BinaryOperator binaryOperator) {
        // nothing to visit for binary operator
    }

    public void visitUnaryExpression(UnaryExpression unaryExpression) {
        scan(unaryExpression.getOperator());
        scan(unaryExpression.getExpression());
    }

    public void visitUnaryOperator(UnaryOperator unaryOperator) {
        // nothing to visit for unary operator
    }

    public void visitNewExpression(NewExpression newExpression) {
        scan(newExpression.getObject());
        scan(newExpression.getPostfix());
    }

    public void visitPostfixExpression(PostfixExpression postfixExpression) {
        scan(postfixExpression.getAwait());
        scan(postfixExpression.getReference());
        scan(postfixExpression.getPostfix());
    }

    public void visitCallPostfix(CallPostfix callPostfix) {
        scan(callPostfix.getArguments());
        scan(callPostfix.getPostfix());
    }

    public void visitDereferencePostfix(DereferencePostfix dereferencePostfix) {
        scan(dereferencePostfix.getIdentifier());
        scan(dereferencePostfix.getPostfix());
    }

    public void visitIndexPostfix(IndexPostfix indexPostfix) {
        scan(indexPostfix.getIndex());
        scan(indexPostfix.getPostfix());
    }

    public void visitReferenceExpression(ReferenceExpression referenceExpression) {
        scan(referenceExpression.getIdentifier());
    }

    public void visitParenthesisExpression(ParenthesisExpression parenthesisExpression) {
        scan(parenthesisExpression.getExpression());
    }

    public void visitTernaryExpression(TernaryExpression ternaryExpression) {
        scan(ternaryExpression.getCondition());
        scan(ternaryExpression.getTrueExpression());
        scan(ternaryExpression.getFalseExpression());
    }

    public void visitPrimitiveUndefined(PrimitiveUndefined primitiveUndefined) {
        // nothing to visit for primitive
    }

    public void visitPrimitiveNull(PrimitiveNull aPrimitiveNull) {
        // nothing to visit for primitive
    }

    public void visitPrimitiveTrue(PrimitiveTrue aPrimitiveTrue) {
        // nothing to visit for primitive
    }

    public void visitPrimitiveFalse(PrimitiveFalse aPrimitiveFalse) {
        // nothing to visit for primitive
    }

    public void visitPrimitiveNumber(PrimitiveNumber primitiveNumber) {
        // nothing to visit for primitive
    }

    public void visitPrimitiveString(PrimitiveString primitiveString) {
        scan(primitiveString.getBody());
    }

    public void visitPrimitiveDate(PrimitiveDate primitiveDate) {
        // nothing to visit for primitive
    }

    public void visitStringLiteral(StringLiteral stringLiteral) {
        // nothing to visit for primitive
    }

    public void visitDefaultArgument(CallPostfix.DefaultArgument defaultArgument) {
        // nothing to visit for default argument
    }

    public void visitAwait(PostfixExpression.Await await) {
        // nothing to visit for await
    }
}