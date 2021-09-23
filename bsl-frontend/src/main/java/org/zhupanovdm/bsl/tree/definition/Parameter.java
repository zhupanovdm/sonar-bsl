package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.PARAMETER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Parameter extends BslTree implements Named {
    private String name;
    private boolean val;
    private BslTree defaultValue;

    public Parameter(CallableDefinition parent) {
        super(parent, PARAMETER);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitParameter(this);
    }

    @Override
    public String toString() {
        return (val ? "Val " : "") + name + (defaultValue == null ? "" : " = " + defaultValue);
    }
}
