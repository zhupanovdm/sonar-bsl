package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.NamedNode;

import static org.zhupanovdm.bsl.tree.BslTree.Type.LABEL;

@Data
@EqualsAndHashCode(callSuper = true)
public class Label extends BslTree implements NamedNode {
    private String name;

    public Label(BslTree parent) {
        super(parent, LABEL);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitLabel(this);
    }

    @Override
    public String toString() {
        return "~" + name;
    }
}
