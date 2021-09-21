package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class ExceptClause extends Statement {
    public ExceptClause(TryStatement parent, Token token) {
        super(token);
        setParent(parent);
        parent.setExceptClause(this);
    }

    @Override
    public String toString() {
        return "Except {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitExceptClause(this);
    }
}