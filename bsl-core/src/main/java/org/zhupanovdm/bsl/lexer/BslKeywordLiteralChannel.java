package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import org.sonar.sslr.channel.Channel;
import org.sonar.sslr.channel.CodeReader;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BslKeywordLiteralChannel extends Channel<Lexer> {

    private final TokenType tokenType;
    private final Set<String> literals;
    private final StringBuilder tmpBuilder = new StringBuilder();
    private final Matcher matcher = Pattern.compile("\\p{javaJavaIdentifierStart}++\\p{javaJavaIdentifierPart}*+").matcher("");
    private final Token.Builder tokenBuilder = Token.builder();

    public BslKeywordLiteralChannel(TokenType tokenType, String... literals) {
        this.tokenType = tokenType;

        this.literals = new HashSet<>();
        for (String literal : literals)
            this.literals.add(literal.toUpperCase());
    }

    @Override
    public boolean consume(CodeReader code, Lexer lexer) {
        if (code.popTo(matcher, tmpBuilder) <= 0)
            return false;

        String word = tmpBuilder.toString();
        String wordOriginal = word;
        word = word.toUpperCase();

        if (literals.contains(word))
            return false;

        Token token = tokenBuilder
                .setType(tokenType)
                .setValueAndOriginalValue(word, wordOriginal)
                .setURI(lexer.getURI())
                .setLine(code.getPreviousCursor().getLine())
                .setColumn(code.getPreviousCursor().getColumn())
                .build();

        lexer.addToken(token);

        tmpBuilder.delete(0, tmpBuilder.length());
        return true;
    }

}
