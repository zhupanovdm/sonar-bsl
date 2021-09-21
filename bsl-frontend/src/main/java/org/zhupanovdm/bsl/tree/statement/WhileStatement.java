package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class WhileStatement extends Statement {
    private Expression condition;

    public WhileStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "While " + condition + " Do {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitWhileStatement(this);
    }
}
