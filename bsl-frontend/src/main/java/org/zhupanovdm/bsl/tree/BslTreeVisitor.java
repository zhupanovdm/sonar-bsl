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
        scan(callableDefinition.getParameters());
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
        scan(forStatement.getInit());
        scan(forStatement.getCondition());
        scan(forStatement.getBody());
    }

    public void visitForEachStatement(ForEachStatement forEachStatement) {
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

    public void visitVariable(Variable variable) {
        // nothing to visit for variable
    }

    public void visitParameter(Parameter parameter) {
        scan(parameter.getDefaultValue());
    }

    public void visitLabel(Label label) {
        // nothing to visit for label
    }

    public void visitCompilationDirective(Directive directive) {
        // nothing to visit for compilation directive
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
        scan(binaryExpression.getLeft());
        scan(binaryExpression.getRight());
    }

    public void visitUnaryExpression(UnaryExpression unaryExpression) {
        scan(unaryExpression.getExpression());
    }

    public void visitNewExpression(NewExpression newExpression) {
        scan(newExpression.getPostfix());
    }

    public void visitPostfixExpression(PostfixExpression postfixExpression) {
        scan(postfixExpression.getPostfix());
    }

    public void visitCallPostfix(CallPostfix callPostfix) {
        scan(callPostfix.getArguments());
        scan(callPostfix.getPostfix());
    }

    public void visitDereferencePostfix(DereferencePostfix dereferencePostfix) {
        scan(dereferencePostfix.getPostfix());
    }

    public void visitIndexPostfix(IndexPostfix indexPostfix) {
        scan(indexPostfix.getIndex());
        scan(indexPostfix.getPostfix());
    }

    public void visitReferenceExpression(ReferenceExpression referenceExpression) {
        // nothing to visit for reference
    }

    public void visitParenthesisExpression(ParenthesisExpression parenthesisExpression) {
        scan(parenthesisExpression.getExpression());
    }

    public void visitTernaryExpression(TernaryExpression ternaryExpression) {
        scan(ternaryExpression.getCondition());
        scan(ternaryExpression.getTrueExpression());
        scan(ternaryExpression.getFalseExpression());
    }

    public void visitEmptyExpression(EmptyExpression emptyExpression) {
        // nothing to visit for empty expression
    }

    public void visitPrimitiveExpression(PrimitiveExpression primitiveExpression) {
        scan(primitiveExpression.getBody());
    }
}