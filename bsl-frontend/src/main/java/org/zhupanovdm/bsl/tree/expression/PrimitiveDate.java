package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrimitiveDate extends PrimitiveExpression {
    private String value;

    public PrimitiveDate(BslTree parent, Token token) {
        super(parent, token);

        String value = token.getOriginalValue();
        this.value = value.substring(1).substring(0, value.length() - 2);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveDate(this);
    }
}
