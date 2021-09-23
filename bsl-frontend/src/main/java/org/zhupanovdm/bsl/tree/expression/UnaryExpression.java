package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnaryExpression extends BslTree {
    private BslTree expression;

    public UnaryExpression(BslTree parent) {
        super(parent, null);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitUnaryExpression(this);
    }

    @Override
    public String toString() {
        return getType() + " " + expression;
    }
}
