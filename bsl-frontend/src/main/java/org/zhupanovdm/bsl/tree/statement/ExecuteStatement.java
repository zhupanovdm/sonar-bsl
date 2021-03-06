package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.EXECUTE_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExecuteStatement extends BslTree {
    private BslTree expression;

    public ExecuteStatement() {
        super(null, EXECUTE_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitExecuteStatement(this);
    }

    @Override
    public String toString() {
        return "Execute(" + expression + ')';
    }
}
