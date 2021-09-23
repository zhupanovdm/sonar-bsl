package org.zhupanovdm.bsl.tree.expression;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.EMPTY;

public class EmptyExpression extends BslTree {
    public EmptyExpression(CallPostfix parent) {
        super(parent, EMPTY);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitEmptyExpression(this);
    }

    @Override
    public String toString() {
        return "<EMPTY EXPR>";
    }
}
