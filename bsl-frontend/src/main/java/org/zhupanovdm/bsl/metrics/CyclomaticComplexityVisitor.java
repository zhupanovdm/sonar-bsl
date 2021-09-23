package org.zhupanovdm.bsl.metrics;

import lombok.Getter;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.expression.BinaryExpression;
import org.zhupanovdm.bsl.tree.expression.TernaryExpression;
import org.zhupanovdm.bsl.tree.statement.*;

import static org.zhupanovdm.bsl.tree.BslTree.Type.AND;
import static org.zhupanovdm.bsl.tree.BslTree.Type.OR;

public class CyclomaticComplexityVisitor extends BslTreeVisitor {
    @Getter
    private int complexity;

    @Override
    public void visitCallableDefinition(CallableDefinition callableDefinition) {
        complexity++;
        super.visitCallableDefinition(callableDefinition);
    }

    @Override
    public void visitIfStatement(IfStatement ifStatement) {
        complexity++;
        super.visitIfStatement(ifStatement);
    }

    @Override
    public void visitElsIfBranch(ElsIfBranch elsIfBranch) {
        complexity++;
        super.visitElsIfBranch(elsIfBranch);
    }

    @Override
    public void visitWhileStatement(WhileStatement whileStatement) {
        complexity++;
        super.visitWhileStatement(whileStatement);
    }

    @Override
    public void visitForStatement(ForStatement forStatement) {
        complexity++;
        super.visitForStatement(forStatement);
    }

    @Override
    public void visitForEachStatement(ForEachStatement forEachStatement) {
        complexity++;
        super.visitForEachStatement(forEachStatement);
    }

    @Override
    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        if (binaryExpression.is(OR, AND)) {
            complexity++;
        }
        super.visitBinaryExpression(binaryExpression);
    }

    @Override
    public void visitTernaryExpression(TernaryExpression ternaryExpression) {
        complexity++;
        super.visitTernaryExpression(ternaryExpression);
    }

    public static int complexity(BslTree tree) {
        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        visitor.scan(tree);
        return visitor.complexity;
    }
}