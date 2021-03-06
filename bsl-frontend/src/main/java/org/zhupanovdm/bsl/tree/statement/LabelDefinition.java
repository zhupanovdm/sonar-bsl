package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.LABEL_DEF;

@Data
@EqualsAndHashCode(callSuper = true)
public class LabelDefinition extends BslTree {
    private Label label;

    public LabelDefinition() {
        super(null, LABEL_DEF);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitLabelDefinition(this);
    }

    @Override
    public String toString() {
        return "~" + label;
    }
}
