package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.GOTO_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class GotoStatement extends BslTree {
    private Label label;

    public GotoStatement() {
        super(null, GOTO_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitGotoStatement(this);
    }

    @Override
    public String toString() {
        return "Goto " + label;
    }
}
