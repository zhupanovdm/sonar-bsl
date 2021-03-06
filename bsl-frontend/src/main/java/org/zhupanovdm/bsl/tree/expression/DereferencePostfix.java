package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.NamedNode;

import static org.zhupanovdm.bsl.tree.BslTree.Type.DEREFERENCE;

@Data
@EqualsAndHashCode(callSuper = true)
public class DereferencePostfix extends Postfix implements NamedNode {
    private String name;

    public DereferencePostfix(BslTree parent) {
        super(parent, DEREFERENCE);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitDereferencePostfix(this);
    }

    @Override
    public String toString() {
        return "." + name + getPostfix().map(Postfix::toString).orElse("");
    }
}
