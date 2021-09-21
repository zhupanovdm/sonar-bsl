package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class PrimitiveNull extends PrimitiveExpression {
    public PrimitiveNull(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "Null";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveNull(this);
    }
}
