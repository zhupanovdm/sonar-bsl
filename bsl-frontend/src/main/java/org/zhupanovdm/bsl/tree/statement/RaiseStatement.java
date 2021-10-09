package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import java.util.Optional;

import static org.zhupanovdm.bsl.tree.BslTree.Type.RAISE_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class RaiseStatement extends BslTree {
    private BslTree expression;

    public RaiseStatement() {
        super(null, RAISE_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitRaiseStatement(this);
    }

    public Optional<BslTree> getExpression() {
        return Optional.ofNullable(expression);
    }

    @Override
    public String toString() {
        return "Raise" + getExpression().map(expr -> " " + expr).orElse("");
    }
}
