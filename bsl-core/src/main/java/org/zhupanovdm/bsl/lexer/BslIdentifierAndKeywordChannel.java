package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import org.sonar.sslr.channel.Channel;
import org.sonar.sslr.channel.CodeReader;
import org.zhupanovdm.bsl.grammar.BslKeyword;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

public class BslIdentifierAndKeywordChannel extends Channel<Lexer> {

    private final Map<String, TokenType> keywordsMap = new HashMap<>();
    private final StringBuilder tmpBuilder = new StringBuilder();
    private final Matcher matcher = Pattern.compile("\\p{javaJavaIdentifierStart}++\\p{javaJavaIdentifierPart}*+").matcher("");
    private final Token.Builder tokenBuilder = Token.builder();

    public BslIdentifierAndKeywordChannel() {
        for (BslKeyword keyword : BslKeyword.values()) {
            keywordsMap.put(keyword.getValue().toUpperCase(), keyword);
            keywordsMap.put(keyword.getValueRu().toUpperCase(), keyword);
        }
    }

    @Override
    public boolean consume(CodeReader code, Lexer lexer) {
        if (code.popTo(matcher, tmpBuilder) <= 0)
            return false;

        String word = tmpBuilder.toString();
        String wordOriginal = word;
        word = word.toUpperCase();

        TokenType keywordType = keywordsMap.get(word);
        Token token = tokenBuilder
                .setType(keywordType == null ? IDENTIFIER : keywordType)
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
