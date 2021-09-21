package org.zhupanovdm.bsl.tree.definition;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class Identifier extends BslTree {
    public Identifier(BslTree parent, Token token) {
        super(parent, token);
    }

    public String getValue() {
        return getToken().getOriginalValue();
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitIdentifier(this);
    }
}
