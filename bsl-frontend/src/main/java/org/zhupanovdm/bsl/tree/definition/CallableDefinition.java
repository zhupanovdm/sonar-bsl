package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.*;

import java.util.LinkedList;
import java.util.List;

import static org.zhupanovdm.bsl.utils.StringUtils.collectionToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class CallableDefinition extends BslTree implements Named, Exportable, HasDirective {
    private String name;
    private final List<Parameter> parameters = new LinkedList<>();
    private Directive directive;
    private boolean export;
    private boolean async;

    public CallableDefinition(Type type) {
        super(null, type);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitCallableDefinition(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (directive != null) {
            builder.append(directive).append(' ');
        }
        if (async) {
            builder.append("Async ");
        }
        String type = getType() == Type.PROCEDURE ? "Procedure" : "Function";
        builder.append(type).append(' ').append(name).append('(');
        collectionToString(builder, parameters, Parameter::toString, ", ").append(')');
        if (export) {
            builder.append(" Export");
        }
        return builder.append(" {").append(getBody().size()).append('}').toString();
    }
}
