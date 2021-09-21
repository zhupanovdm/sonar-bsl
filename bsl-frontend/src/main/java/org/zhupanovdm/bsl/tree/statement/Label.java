package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.definition.Identifier;

@Data
@EqualsAndHashCode(callSuper = true)
public class Label extends BslTree {
    private Identifier identifier;

    public Label(Statement parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "~" + identifier;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitLabel(this);
    }
}
