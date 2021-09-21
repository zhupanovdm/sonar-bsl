package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExecuteStatement extends Statement {
    private Expression expression;

    public ExecuteStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Execute(" + expression + ')';
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitExecuteStatement(this);
    }
}
