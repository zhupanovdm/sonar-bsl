package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class RemoveHandlerStatement extends Statement {
    private Expression event;
    private Expression handler;

    public RemoveHandlerStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "RemoveHandler " + event + ", " + handler;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitRemoveHandlerStatement(this);
    }
}
