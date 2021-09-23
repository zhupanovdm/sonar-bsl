package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.PARENTHESIS;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParenthesisExpression extends BslTree {
    private BslTree expression;

    public ParenthesisExpression(BslTree parent) {
        super(parent, PARENTHESIS);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitParenthesisExpression(this);
    }

    @Override
    public String toString() {
        return "(" + expression + ')';
    }
}
