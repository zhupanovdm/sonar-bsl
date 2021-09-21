package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class PrimitiveUndefined extends PrimitiveExpression {
    public PrimitiveUndefined(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "Undefined";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveUndefined(this);
    }
}
