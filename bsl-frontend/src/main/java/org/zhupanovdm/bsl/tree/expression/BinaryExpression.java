package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private BinaryOperator operator;

    public BinaryExpression(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitBinaryExpression(this);
    }
}
