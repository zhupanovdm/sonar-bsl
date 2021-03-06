package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.zhupanovdm.bsl.utils.StringUtils.collectionToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class CallableDefinition extends BslTree implements NamedNode, Exportable, HasDirective {
    private String name;
    private final List<Parameter> parameters = new LinkedList<>();
    private Directive directive;
    private boolean export;
    private boolean async;

    public CallableDefinition(Type type) {
        super(null, type);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitCallableDefinition(this);
    }

    @Override
    public Optional<Directive> getDirective() {
        return Optional.ofNullable(directive);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        getDirective().ifPresent(d -> builder.append(d).append(' '));
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
