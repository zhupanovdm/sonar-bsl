package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.expression.Expression;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElsIfBranch extends Statement {
    private Expression condition;

    public ElsIfBranch(IfStatement parent, Token token) {
        super(token);
        setParent(parent);
        parent.getElsIfBranches().add(this);
    }

    @Override
    public String toString() {
        return "ElsIf " + condition + " Then {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitElsIfBranch(this);
    }
}
