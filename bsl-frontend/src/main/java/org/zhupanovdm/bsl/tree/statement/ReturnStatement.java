package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import java.util.Optional;

import static org.zhupanovdm.bsl.tree.BslTree.Type.RETURN_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReturnStatement extends BslTree {
    private BslTree expression;

    public ReturnStatement() {
        super(null, RETURN_STMT);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitReturnStatement(this);
    }

    public Optional<BslTree> getExpression() {
        return Optional.ofNullable(expression);
    }

    @Override
    public String toString() {
        return "Return" + getExpression().map(expr -> " " + expr).orElse("");
    }
}
