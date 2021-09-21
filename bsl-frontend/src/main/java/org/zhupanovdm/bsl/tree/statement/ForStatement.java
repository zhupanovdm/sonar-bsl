package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.definition.Identifier;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForStatement extends Statement {
    private Identifier identifier;
    private Expression init;
    private Expression condition;

    public ForStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "For " + identifier + " = " + init + " To " + condition + " Do {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitForStatement(this);
    }
}
