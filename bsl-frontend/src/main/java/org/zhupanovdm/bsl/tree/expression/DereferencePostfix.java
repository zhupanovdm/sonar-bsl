package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.DEREFERENCE;

@Data
@EqualsAndHashCode(callSuper = true)
public class DereferencePostfix extends Postfix implements Named {
    private String name;

    public DereferencePostfix(BslTree parent) {
        super(parent, DEREFERENCE);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitDereferencePostfix(this);
    }

    @Override
    public String toString() {
        return "." + name + (getPostfix() == null ? "" : getPostfix());
    }
}
