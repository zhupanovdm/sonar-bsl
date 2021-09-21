package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnaryOperator extends BslTree {
    private Type value;

    public UnaryOperator(UnaryExpression parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return value.name();
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitUnaryOperator(this);
    }

    public enum Type {
        MINUS, NOT
    }
}
