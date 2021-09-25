package org.zhupanovdm.bsl.tree.statement;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.TRY_STMT;

public class ExceptClause extends BslTree {
    public ExceptClause(TryStatement parent) {
        super(parent, TRY_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitExceptClause(this);
    }

    @Override
    public String toString() {
        return "Except {" + getBody().size() + "}";
    }
}