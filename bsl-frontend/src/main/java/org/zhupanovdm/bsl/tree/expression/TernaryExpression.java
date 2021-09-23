package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.HasCondition;

import static org.zhupanovdm.bsl.tree.BslTree.Type.TERNARY;

@Data
@EqualsAndHashCode(callSuper = true)
public class TernaryExpression extends BslTree implements HasCondition {
    private BslTree condition;
    private BslTree trueExpression;
    private BslTree falseExpression;

    public TernaryExpression(BslTree parent) {
        super(parent, TERNARY);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitTernaryExpression(this);
    }

    @Override
    public String toString() {
        return "?(" + condition + ", " + trueExpression + ", " + falseExpression + ')';
    }
}
