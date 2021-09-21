package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.tree.BslTree;

@NoArgsConstructor
public abstract class Statement extends BslTree {
    public Statement(Token token) {
        super(null, token);
    }
}
