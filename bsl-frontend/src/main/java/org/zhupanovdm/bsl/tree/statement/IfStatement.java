package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.HasCondition;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.zhupanovdm.bsl.tree.BslTree.Type.IF_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class IfStatement extends BslTree implements HasCondition {
    private BslTree condition;
    private final List<ElsIfBranch> elsIfBranches = new LinkedList<>();
    private ElseClause elseClause;

    public IfStatement() {
        super(null, IF_STMT);
    }

    public Optional<ElseClause> getElseClause() {
        return Optional.ofNullable(elseClause);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitIfStatement(this);
    }

    @Override
    public String toString() {
        return "If " + condition + " Then {" + getBody().size() + "}";
    }
}
