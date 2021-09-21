package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddHandlerStatement extends Statement {
    private Expression event;
    private Expression handler;

    public AddHandlerStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "AddHandler " + event + ", " + handler;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitAddHandlerStatement(this);
    }
}
