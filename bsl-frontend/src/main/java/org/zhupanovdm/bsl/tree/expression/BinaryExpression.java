package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class BinaryExpression extends BslTree {
    private BslTree left;
    private BslTree right;

    public BinaryExpression(BslTree parent) {
        super(parent, null);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitBinaryExpression(this);
    }

    @Override
    public String toString() {
        return left + " " + getType() + " " + right;
    }
}
