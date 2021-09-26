package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.POSTFIX;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostfixExpression extends Postfix {
    private ReferenceExpression reference;
    private boolean await;

    public PostfixExpression(BslTree parent) {
        super(parent, POSTFIX);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitPostfixExpression(this);
    }

    @Override
    public BslToken getFirstToken() {
        if (getTokens().isEmpty()) {
            return reference.getFirstToken();
        }
        return getTokens().get(0);
    }

    @Override
    public String toString() {
        return (await ? "Await " : "") + reference + (getPostfix() == null ? "" : getPostfix());
    }
}
