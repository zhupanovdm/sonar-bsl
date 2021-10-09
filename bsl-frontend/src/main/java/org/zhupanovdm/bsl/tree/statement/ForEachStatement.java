package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.definition.Variable;

import static org.zhupanovdm.bsl.tree.BslTree.Type.FOREACH_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForEachStatement extends BslTree {
    private Variable variable;
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
        return "For each " + variable + " In " + collection + " Do {" + getBody().size() + "}";
    }
}
