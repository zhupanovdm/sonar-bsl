package org.zhupanovdm.bsl.tree.statement;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.EMPTY_STMT;

public class EmptyStatement extends BslTree {
    public EmptyStatement() {
        super(null, EMPTY_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitEmptyStatement(this);
    }

    @Override
    public String toString() {
        return "<EMPTY STMT>";
    }
}