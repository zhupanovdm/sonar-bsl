package org.zhupanovdm.bsl.tree.definition;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.*;

import static org.zhupanovdm.bsl.utils.StringUtils.collectionToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class VariablesDefinition extends BslTree {
    private CompilationDirective directive;

    public VariablesDefinition(Token token) {
        super(null, token);
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

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitVariablesDefinition(this);
    }
}
