package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.PostfixExpression;

@Data
@EqualsAndHashCode(callSuper = true)
public class CallStatement extends Statement {
    private PostfixExpression expression;

    public CallStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "" + expression;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitCallStatement(this);
    }
}
