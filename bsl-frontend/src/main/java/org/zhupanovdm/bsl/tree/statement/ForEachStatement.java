package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.definition.Identifier;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForEachStatement extends Statement {
    private Identifier identifier;
    private Expression collection;

    public ForEachStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "For each " + identifier + " In " + collection + " Do {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitForEachStatement(this);
    }
}
