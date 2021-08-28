package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

public enum BslTokenType implements TokenType {
    NUMERIC_LITERAL,
    DATE_LITERAL;

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
