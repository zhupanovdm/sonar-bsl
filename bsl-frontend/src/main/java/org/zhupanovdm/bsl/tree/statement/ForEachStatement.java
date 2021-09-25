package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.Named;

import static org.zhupanovdm.bsl.tree.BslTree.Type.FOREACH_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForEachStatement extends BslTree implements Named {
    private String name;
    private BslTree collection;

    public ForEachStatement() {
        super(null, FOREACH_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitForEachStatement(this);
    }

    @Override
    public String toString() {
        return "For each " + name + " In " + collection + " Do {" + getBody().size() + "}";
    }
}
