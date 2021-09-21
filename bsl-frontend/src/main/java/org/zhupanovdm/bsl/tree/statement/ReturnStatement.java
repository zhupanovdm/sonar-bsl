package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReturnStatement extends Statement {
    private Expression expression;

    public ReturnStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Return" + (expression == null ? "" : " " + expression);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitReturnStatement(this);
    }
}
