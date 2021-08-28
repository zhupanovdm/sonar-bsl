package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;
import org.zhupanovdm.bsl.api.BslKeyword;
import org.zhupanovdm.bsl.api.BslPunctuator;

import java.nio.charset.Charset;

import static org.zhupanovdm.bsl.api.BslPunctuator.AMP;
import static org.zhupanovdm.bsl.api.BslPunctuator.HASH;

public final class BslLexer {

    private BslLexer() {
    }

    public static Lexer create(Charset charset) {
        return Lexer.builder()
                .withCharset(charset)

                .withChannel(new BlackHoleChannel("\\s++"))

                /*
                // Comments
                .withChannel(commentRegexp(COMMENT_REGEXP))

                // Literals
                .withChannel(regexp(LITERAL, STRING_REGEXP))
                .withChannel(regexp(CONSTANT, NUMBER_REGEXP))
                .withChannel(regexp(CONSTANT, DATE_REGEXP))
                 */

                .withChannel(new BslIdentifierAndKeywordChannel())

                .withChannel(new PunctuatorChannel(punctuators()))

                .withChannel(new UnknownCharacterChannel())
                .build();
    }

    private static TokenType[] punctuators() {
        TokenType[] result = new TokenType[BslPunctuator.values().length - 2];
        int i = 0;
        for (TokenType tokenType : BslPunctuator.values()) {
            if (tokenType != HASH && tokenType != AMP)
                result[i++] = tokenType;
        }
        return result;
    }

    public static String[] keywords() {
        BslKeyword[] words = BslKeyword.values();
        String[] keywordsValue = new String[words.length * 2];
        for (int i = 0; i < words.length; i++) {
            String value = words[i].getValue().toUpperCase();
            keywordsValue[2 * i] = value;
            if (words[i].getValueAlt() == null) {
                keywordsValue[2 * i + 1] = value;
            } else {
                keywordsValue[2 * i + 1] = words[i].getValueAlt().toUpperCase();
            }
        }
        return keywordsValue;
    }


}
