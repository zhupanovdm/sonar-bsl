package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class IfStatement extends Statement {
    private Expression condition;
    private final List<ElsIfBranch> elsIfBranches = new LinkedList<>();
    private ElseClause elseClause;

    public IfStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "If " + condition + " Then {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitIfStatement(this);
    }
}
