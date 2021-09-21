package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@EqualsAndHashCode(callSuper = true)
public class ElseClause extends Statement {
    public ElseClause(IfStatement parent, Token token) {
        super(token);
        setParent(parent);
        parent.setElseClause(this);
    }

    @Override
    public String toString() {
        return "Else {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitElseClause(this);
    }
}