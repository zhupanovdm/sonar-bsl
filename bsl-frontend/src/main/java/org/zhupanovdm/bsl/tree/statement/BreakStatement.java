package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.BREAK_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class BreakStatement extends BslTree {
    public BreakStatement() {
        super(null, BREAK_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitBreakStatement(this);
    }

    @Override
    public String toString() {
        return "Break";
    }
}
