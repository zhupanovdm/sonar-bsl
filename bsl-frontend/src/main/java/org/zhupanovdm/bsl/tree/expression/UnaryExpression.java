package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnaryExpression extends Expression {
    private Expression expression;
    private UnaryOperator operator;

    public UnaryExpression(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return operator + " " + expression;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitUnaryExpression(this);
    }
}
