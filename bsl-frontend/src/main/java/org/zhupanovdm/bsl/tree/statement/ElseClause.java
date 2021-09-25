package org.zhupanovdm.bsl.tree.statement;

import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.IF_STMT;

@EqualsAndHashCode(callSuper = true)
public class ElseClause extends BslTree {
    public ElseClause(IfStatement parent) {
        super(parent, IF_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitElseClause(this);
    }

    @Override
    public String toString() {
        return "Else {" + getBody().size() + "}";
    }
}