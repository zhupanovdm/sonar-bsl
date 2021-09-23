package org.zhupanovdm.bsl.tree.expression;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.INDEX;

@Data
@EqualsAndHashCode(callSuper = true)
public class IndexPostfix extends Postfix {
    private BslTree index;

    public IndexPostfix(BslTree parent) {
        super(parent, INDEX);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitIndexPostfix(this);
    }

    @Override
    public String toString() {
        return "[" + index + ']' + (getPostfix() == null ? "" : getPostfix());
    }
}
