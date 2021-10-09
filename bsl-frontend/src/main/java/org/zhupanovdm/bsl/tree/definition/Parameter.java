package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.Named;

import java.util.Optional;

import static org.zhupanovdm.bsl.tree.BslTree.Type.PARAMETER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Parameter extends BslTree implements Named {
    private String name;
    private boolean val;
    private int index;
    private BslTree defaultValue;

    public Parameter(CallableDefinition parent) {
        super(parent, PARAMETER);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitParameter(this);
    }

    public Optional<BslTree> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @Override
    public String toString() {
        return (val ? "Val " : "") + name + getDefaultValue().map(expr -> " = " + expr).orElse("");
    }
}
