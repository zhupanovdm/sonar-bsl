package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class PrimitiveFalse extends PrimitiveExpression {
    public PrimitiveFalse(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "False";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveFalse(this);
    }
}
