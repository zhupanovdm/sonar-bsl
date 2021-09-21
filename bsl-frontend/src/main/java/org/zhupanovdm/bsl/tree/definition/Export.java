package org.zhupanovdm.bsl.tree.definition;

import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class Export extends BslTree {
    public Export(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "Export";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitExport(this);
    }
}