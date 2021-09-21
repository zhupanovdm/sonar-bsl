package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import java.util.LinkedList;
import java.util.List;

import static org.zhupanovdm.bsl.utils.StringUtils.collectionToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class CallPostfix extends Postfix {
    private final List<Expression> arguments = new LinkedList<>();

    public CallPostfix(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitCallPostfix(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append('(');
        collectionToString(builder, arguments, Expression::toString, ", ");
        builder.append(')');

        if (getPostfix() != null) {
            builder.append(getPostfix());
        }
        return builder.toString();
    }

    public static class DefaultArgument extends Expression {
        public DefaultArgument(CallPostfix parent, Token token) {
            super(parent, token);
            parent.getArguments().add(this);
        }

        @Override
        public String toString() {
            return "<default>";
        }

        @Override
        public void accept(BslTreeVisitor visitor) {
            visitor.visitDefaultArgument(this);
        }
    }
}
