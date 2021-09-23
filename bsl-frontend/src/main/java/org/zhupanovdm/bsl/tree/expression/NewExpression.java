package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.NEW;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewExpression extends Postfix implements Named {
    private String name;

    public NewExpression(BslTree parent) {
        super(parent, NEW);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitNewExpression(this);
    }

    @Override
    public String toString() {
        return "New " + name + (getPostfix() == null ? "" : getPostfix());
    }
}
