package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class TernaryExpression extends Expression {
    private Expression condition;
    private Expression trueExpression;
    private Expression falseExpression;

    public TernaryExpression(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "?(" + condition + ", " + trueExpression + ", " + falseExpression + ')';
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitTernaryExpression(this);
    }
}
