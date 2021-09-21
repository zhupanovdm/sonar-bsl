package org.zhupanovdm.bsl.tree.definition;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class Variable extends BslTree {
    private Identifier identifier;
    private Export export;

    public Variable(VariablesDefinition parent, Token token) {
        super(parent, token);
        parent.getBody().add(this);
    }

    @Override
    public String toString() {
        return identifier + (export == null ? "" : " " + export);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitVariable(this);
    }
}
