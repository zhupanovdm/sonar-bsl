package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.utils.StringUtils;

@EqualsAndHashCode(callSuper = true)
public class PrimitiveString extends PrimitiveExpression {
    public PrimitiveString(BslTree parent, Token token) {
        super(parent, token);
    }

    public String getValue() {
        return StringUtils.collectionToString(getBody(), BslTree::toString, "");
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPrimitiveString(this);
    }
}
