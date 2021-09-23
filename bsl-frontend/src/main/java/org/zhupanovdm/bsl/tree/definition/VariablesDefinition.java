package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.HasDirective;

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
    public void accept(BslTreeVisitor visitor) {
        visitor.visitVariablesDefinition(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (getDirective() != null) {
            builder.append(getDirective()).append(' ');
        }
        builder.append("Var ");
        return collectionToString(builder, getBody(), Object::toString, ", ").toString();
    }
}
