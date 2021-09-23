package org.zhupanovdm.bsl.tree.statement;

import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.IF_STMT;

@EqualsAndHashCode(callSuper = true)
public class ElseClause extends BslTree {
    public ElseClause(IfStatement parent) {
        super(parent, IF_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitElseClause(this);
    }

    @Override
    public String toString() {
        return "Else {" + getBody().size() + "}";
    }
}