package org.zhupanovdm.bsl.tree.definition;

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
public class CallableDefinition extends BslTree {
    private Type type;
    private Identifier identifier;
    private final List<Parameter> parameters = new LinkedList<>();
    private CompilationDirective directive;
    private Async async;
    private Export export;

    public CallableDefinition(Token token) {
        super(null, token);
        this.type = Type.FUNCTION;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (directive != null) {
            builder.append(directive).append(' ');
        }
        if (async != null) {
            builder.append(async).append(' ');
        }
        builder.append(type).append(' ').append(identifier).append('(');
        collectionToString(builder, parameters, Parameter::toString, ", ").append(')');
        if (export != null) {
            builder.append(' ').append(export);
        }
        return builder.append(" {").append(getBody().size()).append('}').toString();
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitCallableDefinition(this);
    }

    public enum Type { FUNCTION, PROCEDURE }

    public static class Async extends BslTree {
        public Async(CallableDefinition parent, Token token) {
            super(parent, token);
        }

        @Override
        public String toString() {
            return "Async";
        }

        @Override
        public void accept(BslTreeVisitor visitor) {
            visitor.visitCallableAsync(this);
        }
    }
}
