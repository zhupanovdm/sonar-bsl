package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.HasDirective;

import java.util.Optional;

import static org.zhupanovdm.bsl.tree.BslTree.Type.VAR_DEF;
import static org.zhupanovdm.bsl.utils.StringUtils.collectionToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class VariablesDefinition extends BslTree implements HasDirective {
    private Directive directive;

    public VariablesDefinition() {
        super(null, VAR_DEF);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitVariablesDefinition(this);
    }

    @Override
    public Optional<Directive> getDirective() {
        return Optional.ofNullable(directive);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        getDirective().ifPresent(d -> builder.append(d).append(' '));
        builder.append("Var ");
        return collectionToString(builder, getBody(), Object::toString, ", ").toString();
    }
}
