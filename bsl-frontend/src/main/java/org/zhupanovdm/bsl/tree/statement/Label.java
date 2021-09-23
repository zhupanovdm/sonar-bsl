package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.LABEL;

@Data
@EqualsAndHashCode(callSuper = true)
public class Label extends BslTree implements Named {
    private String name;

    public Label(BslTree parent) {
        super(parent, LABEL);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitLabel(this);
    }

    @Override
    public String toString() {
        return "~" + name;
    }
}
