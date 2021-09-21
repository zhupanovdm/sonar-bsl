package org.zhupanovdm.bsl.grammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;

public enum BslPunctuator implements TokenType, GrammarRuleKey {
    HASH("#"),
    AMP("&"),
    QUESTION("?"),
    QUOTE("\""),
    PIPE("|"),
    TILDA("~"),
    COLON(":"),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    LBRACK("["),
    RBRACK("]"),
    COMMA(","),
    DOT("."),
    PLUS("+"),
    MINUS("-"),
    MUL("*"),
    DIV("/"),
    MOD("%"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    EQ("="),
    NEQ("<>");

    private final String value;

    BslPunctuator(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
        return false;
    }
}
