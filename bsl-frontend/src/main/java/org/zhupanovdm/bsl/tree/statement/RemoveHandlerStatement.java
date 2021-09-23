package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.REM_HANDLER_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class RemoveHandlerStatement extends BslTree {
    private BslTree event;
    private BslTree handler;

    public RemoveHandlerStatement() {
        super(null, REM_HANDLER_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitRemoveHandlerStatement(this);
    }

    @Override
    public String toString() {
        return "RemoveHandler " + event + ", " + handler;
    }
}
