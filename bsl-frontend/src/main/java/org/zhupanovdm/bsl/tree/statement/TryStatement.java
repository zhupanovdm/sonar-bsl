package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.TRY_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class TryStatement extends BslTree {
    private ExceptClause exceptClause;

    public TryStatement() {
        super(null, TRY_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitTryStatement(this);
    }

    @Override
    public String toString() {
        return "Try {" + getBody().size() + "}";
    }
}
