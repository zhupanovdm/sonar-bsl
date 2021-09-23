package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.POSTFIX;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostfixExpression extends Postfix implements Named {
    private String name;
    private boolean await;

    public PostfixExpression(BslTree parent) {
        super(parent, POSTFIX);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPostfixExpression(this);
    }

    @Override
    public String toString() {
        return (await ? "Await " : "") + name + (getPostfix() == null ? "" : getPostfix());
    }
}
