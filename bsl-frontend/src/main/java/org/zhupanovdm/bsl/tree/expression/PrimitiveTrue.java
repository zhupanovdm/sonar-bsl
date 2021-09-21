package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class PrimitiveTrue extends PrimitiveExpression {
    public PrimitiveTrue(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "True";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveTrue(this);
    }
}
