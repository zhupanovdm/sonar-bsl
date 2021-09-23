package org.zhupanovdm.bsl.tree.statement;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.TRY_STMT;

public class ExceptClause extends BslTree {
    public ExceptClause(TryStatement parent) {
        super(parent, TRY_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitExceptClause(this);
    }

    @Override
    public String toString() {
        return "Except {" + getBody().size() + "}";
    }
}