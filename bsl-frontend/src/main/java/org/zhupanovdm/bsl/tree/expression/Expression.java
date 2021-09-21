package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.tree.BslTree;

@NoArgsConstructor
public abstract class Expression extends BslTree {
    public Expression(BslTree parent, Token token) {
        super(parent, token);
    }
}