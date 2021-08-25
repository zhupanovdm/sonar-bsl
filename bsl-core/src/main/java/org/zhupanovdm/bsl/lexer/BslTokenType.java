package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

public enum BslTokenType implements TokenType {
    DIRECTIVE,
    PREPROCESSOR;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return name();
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
        return false;
    }
}
