package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import org.sonar.sslr.channel.Channel;
import org.sonar.sslr.channel.CodeReader;
import org.zhupanovdm.bsl.api.BslWord;
import org.zhupanovdm.bsl.api.BslKeyword;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sonar.sslr.api.GenericTokenType.CONSTANT;
import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;
import static org.zhupanovdm.bsl.api.BslKeyword.*;

public class BslIdentifierAndKeywordChannel extends Channel<Lexer> {

    private final Map<String, TokenType> tokenMap = new HashMap<>();
    private final StringBuilder tmpBuilder = new StringBuilder();
    private final Matcher matcher = Pattern.compile("\\p{javaJavaIdentifierStart}++\\p{javaJavaIdentifierPart}*+").matcher("");
    private final Token.Builder tokenBuilder = Token.builder();

    public BslIdentifierAndKeywordChannel() {
        for (BslWord keyword : BslKeyword.keywords()) {
            TokenType type = keyword;
            if (keyword.equals(TRUE) ||
                    keyword.equals(FALSE) ||
                    keyword.equals(UNDEFINED) ||
                    keyword.equals(NULL))
                type = CONSTANT;
            tokenMap.put(keyword.getValue().toUpperCase(), type);
            tokenMap.put(keyword.getValueAlt().toUpperCase(), type);
        }

        tokenMap.put(ASYNC.getValue().toUpperCase(), ASYNC);
        tokenMap.put(ASYNC.getValueAlt().toUpperCase(), ASYNC);

        tokenMap.put(AWAIT.getValue().toUpperCase(), AWAIT);
        tokenMap.put(AWAIT.getValueAlt().toUpperCase(), AWAIT);
    }

    @Override
    public boolean consume(CodeReader code, Lexer lexer) {
        if (code.popTo(matcher, tmpBuilder) <= 0)
            return false;

        String word = tmpBuilder.toString();
        String wordOriginal = word;
        word = word.toUpperCase();

        TokenType keywordType = tokenMap.get(word);
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
