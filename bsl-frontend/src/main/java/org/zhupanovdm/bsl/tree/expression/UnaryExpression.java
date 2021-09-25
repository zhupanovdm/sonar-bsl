package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnaryExpression extends BslTree {
    private BslTree expression;

    public UnaryExpression(BslTree parent) {
        super(parent, null);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitUnaryExpression(this);
    }

    @Override
    public String toString() {
        return getType() + " " + expression;
    }
}
