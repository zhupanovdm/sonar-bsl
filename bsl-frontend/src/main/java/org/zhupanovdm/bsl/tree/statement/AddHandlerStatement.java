package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.ADD_HANDLER_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddHandlerStatement extends BslTree {
    private BslTree event;
    private BslTree handler;

    public AddHandlerStatement() {
        super(null, ADD_HANDLER_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitAddHandlerStatement(this);
    }

    @Override
    public String toString() {
        return "AddHandler " + event + ", " + handler;
    }
}
