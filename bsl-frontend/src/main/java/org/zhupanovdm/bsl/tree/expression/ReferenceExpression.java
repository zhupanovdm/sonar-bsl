package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.REFERENCE;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReferenceExpression extends BslTree implements Named {
    private String name;

    public ReferenceExpression(BslTree parent) {
        super(parent, REFERENCE);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitReferenceExpression(this);
    }

    @Override
    public String toString() {
        return "" + name;
    }
}