package org.zhupanovdm.bsl.metrics;

import lombok.Getter;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.expression.BinaryExpression;
import org.zhupanovdm.bsl.tree.expression.TernaryExpression;
import org.zhupanovdm.bsl.tree.statement.*;

import static org.zhupanovdm.bsl.tree.BslTree.Type.AND;
import static org.zhupanovdm.bsl.tree.BslTree.Type.OR;

public class CyclomaticComplexity implements BslTreeSubscriber {
    @Getter
    private int complexity;

    @Override
    public void init() {
        complexity = 0;
    }

    @Override
    public void onVisitCallableDefinition(CallableDefinition def) {
        complexity++;
    }

    @Override
    public void onVisitIfStatement(IfStatement stmt) {
        complexity++;
    }

    @Override
    public void onVisitElsIfBranch(ElsIfBranch branch) {
        complexity++;
    }

    @Override
    public void onVisitWhileStatement(WhileStatement stmt) {
        complexity++;
    }

    @Override
    public void onVisitForStatement(ForStatement stmt) {
        complexity++;
    }

    @Override
    public void onVisitForEachStatement(ForEachStatement stmt) {
        complexity++;
    }

    @Override
    public void onVisitBinaryExpression(BinaryExpression expr) {
        if (expr.is(OR, AND)) {
            complexity++;
        }
    }

    @Override
    public void onVisitTernaryExpression(TernaryExpression expr) {
        complexity++;
    }
}