package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrimitiveNumber extends PrimitiveExpression {
    private String value;

    public PrimitiveNumber(BslTree parent, Token token) {
        super(parent, token);

        this.value = token.getOriginalValue();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveNumber(this);
    }
}
