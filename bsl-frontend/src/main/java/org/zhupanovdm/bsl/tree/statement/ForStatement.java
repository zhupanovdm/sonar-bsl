package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.HasCondition;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.FOR_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForStatement extends BslTree implements Named, HasCondition {
    private String name;
    private BslTree init;
    private BslTree condition;

    public ForStatement() {
        super(null, FOR_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitForStatement(this);
    }

    @Override
    public String toString() {
        return "For " + name + " = " + init + " To " + condition + " Do {" + getBody().size() + "}";
    }
}
