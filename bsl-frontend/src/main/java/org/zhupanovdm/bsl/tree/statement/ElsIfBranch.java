package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.HasCondition;

import static org.zhupanovdm.bsl.tree.BslTree.Type.IF_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElsIfBranch extends BslTree implements HasCondition {
    private BslTree condition;

    public ElsIfBranch(IfStatement parent) {
        super(parent, IF_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitElsIfBranch(this);
    }

    @Override
    public String toString() {
        return "ElsIf " + condition + " Then {" + getBody().size() + "}";
    }
}
