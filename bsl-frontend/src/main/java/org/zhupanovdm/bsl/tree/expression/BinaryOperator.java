package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class BinaryOperator extends BslTree {
    private Type value;

    public BinaryOperator(BinaryExpression parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return value.name();
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitBinaryOperator(this);
    }

    public enum Type { AND, OR, ADD, SUB, MUL, DIV, MOD, EQ, GT, GE, LT, LE, NEQ }
}
