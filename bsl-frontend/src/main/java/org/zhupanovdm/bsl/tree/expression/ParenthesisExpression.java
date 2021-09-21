package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParenthesisExpression extends Expression {
    private Expression expression;

    public ParenthesisExpression(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "(" + expression + ')';
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitParenthesisExpression(this);
    }
}
