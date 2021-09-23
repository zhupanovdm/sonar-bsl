package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.PostfixExpression;

import static org.zhupanovdm.bsl.tree.BslTree.Type.ASSIGN_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssignmentStatement extends BslTree {
    private PostfixExpression target;
    private BslTree expression;

    public AssignmentStatement() {
        super(null, ASSIGN_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitAssignmentStatement(this);
    }

    @Override
    public String toString() {
        return target + " = " + expression;
    }
}
