package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class EmptyStatement extends Statement {
    public EmptyStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "<empty>";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitEmptyStatement(this);
    }
}