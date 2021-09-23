package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.PostfixExpression;

import static org.zhupanovdm.bsl.tree.BslTree.Type.CALL_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class CallStatement extends BslTree {
    private PostfixExpression expression;

    public CallStatement() {
        super(null, CALL_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitCallStatement(this);
    }

    @Override
    public String toString() {
        return "" + expression;
    }
}
