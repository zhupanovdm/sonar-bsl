package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.PostfixExpression;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssignmentStatement extends Statement {
    private PostfixExpression target;
    private Expression expression;

    public AssignmentStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return target + " = " + expression;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitAssignmentStatement(this);
    }
}
