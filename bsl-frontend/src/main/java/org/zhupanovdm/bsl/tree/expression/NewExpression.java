package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewExpression extends Postfix {
    private ReferenceExpression object;

    public NewExpression(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "New " + object + (getPostfix() == null ? "" : getPostfix());
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitNewExpression(this);
    }
}
