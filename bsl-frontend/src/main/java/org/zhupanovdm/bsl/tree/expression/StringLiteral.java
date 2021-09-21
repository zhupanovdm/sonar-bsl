package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class StringLiteral extends BslTree {
    private String value;

    public StringLiteral(PrimitiveString parent, Token token, String value) {
        super(parent, token);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitStringLiteral(this);
    }
}
