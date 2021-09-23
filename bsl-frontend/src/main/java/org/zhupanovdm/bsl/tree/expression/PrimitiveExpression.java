package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrimitiveExpression extends BslTree {
    public String value;

    public PrimitiveExpression(BslTree parent, Type type) {
        super(parent, type);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveExpression(this);
    }

    @Override
    public String toString() {
        return value;
    }
}