package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostfixExpression extends Postfix {
    private ReferenceExpression reference;
    private Await await;

    public PostfixExpression(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return (await == null ? "" : await + " ") + reference + (getPostfix() == null ? "" : getPostfix());
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPostfixExpression(this);
    }

    public static class Await extends BslTree {
        public Await(PostfixExpression parent, Token token) {
            super(parent, token);
        }

        @Override
        public String toString() {
            return "Await";
        }

        @Override
        public void accept(BslTreeVisitor visitor) {
            visitor.visitAwait(this);
        }
    }
}
