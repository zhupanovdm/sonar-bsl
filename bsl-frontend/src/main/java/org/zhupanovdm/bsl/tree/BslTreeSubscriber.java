package org.zhupanovdm.bsl.tree;

import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.tree.definition.*;
import org.zhupanovdm.bsl.tree.expression.*;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;
import org.zhupanovdm.bsl.tree.statement.*;

public interface BslTreeSubscriber {
    default void init() {}

    default void onEnterFile(AbstractModuleContext context) {}
    default void onLeaveFile(AbstractModuleContext context) {}

    default void onEnterNode(BslTree node) {}
    default void onLeaveNode(BslTree node) {}

    default void onVisitModule(ModuleRoot module) {}
    default void onVisitPreprocessorIf(PreprocessorIf pp) {}
    default void onVisitPreprocessorElsif(PreprocessorElsif pp) {}

    default void onVisitCallableDefinition(CallableDefinition def) {}
    default void onVisitVariablesDefinition(VariablesDefinition def) {}

    default void onVisitAssignmentStatement(AssignmentStatement stmt) {}
    default void onVisitCallStatement(CallStatement stmt) {}
    default void onVisitIfStatement(IfStatement stmt) {}
    default void onVisitElsIfBranch(ElsIfBranch branch) {}
    default void onVisitElseClause(ElseClause clause) {}
    default void onVisitWhileStatement(WhileStatement stmt) {}
    default void onVisitForStatement(ForStatement stmt) {}
    default void onVisitForEachStatement(ForEachStatement stmt) {}
    default void onVisitReturnStatement(ReturnStatement stmt) {}
    default void onVisitContinueStatement(ContinueStatement stmt) {}
    default void onVisitBreakStatement(BreakStatement stmt) {}
    default void onVisitRaiseStatement(RaiseStatement stmt) {}
    default void onVisitEmptyStatement(EmptyStatement stmt) {}
    default void onVisitTryStatement(TryStatement stmt) {}
    default void onVisitExceptClause(ExceptClause clause) {}
    default void onVisitExecuteStatement(ExecuteStatement stmt) {}
    default void onVisitGotoStatement(GotoStatement stmt) {}
    default void onVisitAddHandlerStatement(AddHandlerStatement stmt) {}
    default void onVisitRemoveHandlerStatement(RemoveHandlerStatement stmt) {}
    default void onVisitLabelDefinition(LabelDefinition def) {}

    default void onVisitBinaryExpression(BinaryExpression expr) {}
    default void onVisitUnaryExpression(UnaryExpression expr) {}
    default void onVisitNewExpression(NewExpression expr) {}
    default void onVisitPostfixExpression(PostfixExpression expr) {}
    default void onVisitCallPostfix(CallPostfix postfix) {}
    default void onVisitDereferencePostfix(DereferencePostfix postfix) {}
    default void onVisitIndexPostfix(IndexPostfix postfix) {}
    default void onVisitReferenceExpression(ReferenceExpression expr) {}
    default void onVisitParenthesisExpression(ParenthesisExpression expr) {}
    default void onVisitTernaryExpression(TernaryExpression expr) {}
    default void onVisitEmptyExpression(EmptyExpression expr) {}
    default void onVisitPrimitiveExpression(PrimitiveExpression expr) {}

    default void onVisitVariable(Variable variable) {}
    default void onVisitParameter(Parameter parameter) {}
    default void onVisitDirective(Directive directive) {}
    default void onVisitLabel(Label label) {}
}