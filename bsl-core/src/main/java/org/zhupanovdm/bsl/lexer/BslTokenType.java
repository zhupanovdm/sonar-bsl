package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;

public enum BslTokenType implements TokenType, GrammarRuleKey {
    NUMERIC_LITERAL,
    BOOLEAN_LITERAL,
    DATE_LITERAL,
    NULL_LITERAL,
    UNDEFINED_LITERAL;

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
