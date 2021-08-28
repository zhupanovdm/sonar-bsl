package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

public enum BslTokenType implements TokenType {
    WHITESPACE(""),
    IDENTIFIER(""),
    COMMENT(""),
    STRING_LITERAL(""),
    NUMBER_LITERAL(""),
    DATE_LITERAL("");

    BslTokenType(String regexp) {
    }

    private String regexp;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return name();
    }

    public String getRegexp() {
        return regexp;
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
        return false;
    }
}
