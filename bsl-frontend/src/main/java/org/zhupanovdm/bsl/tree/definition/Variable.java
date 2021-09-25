package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.Exportable;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.VARIABLE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Variable extends BslTree implements Named, Exportable {
    private String name;
    private boolean export;

    public Variable(VariablesDefinition parent) {
        super(parent, VARIABLE);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitVariable(this);
    }

    @Override
    public String toString() {
        return name + (export ? " Export" : "");
    }
}
