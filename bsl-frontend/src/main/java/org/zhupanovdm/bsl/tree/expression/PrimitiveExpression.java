package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.tree.BslTree;

@NoArgsConstructor
public abstract class PrimitiveExpression extends Expression {
    public PrimitiveExpression(BslTree parent, Token token) {
        super(parent, token);
    }
}