package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;
import org.zhupanovdm.bsl.grammar.BslPunctuator;

import java.nio.charset.Charset;

import static com.sonar.sslr.api.GenericTokenType.CONSTANT;
import static com.sonar.sslr.api.GenericTokenType.LITERAL;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;
import static org.zhupanovdm.bsl.grammar.BslGrammar.*;
import static org.zhupanovdm.bsl.grammar.BslPunctuator.AMP;
import static org.zhupanovdm.bsl.grammar.BslPunctuator.HASH;

public final class BslLexer {

    private BslLexer() {
    }

    public static Lexer create(Charset charset) {
        return Lexer.builder()
                .withCharset(charset)

                .withChannel(new BlackHoleChannel("\\s++"))

                // Comments
                .withChannel(commentRegexp(COMMENT_REGEXP))

                // Literals
                .withChannel(regexp(LITERAL, STRING_REGEXP))
                .withChannel(regexp(CONSTANT, NUMBER_REGEXP))
                .withChannel(regexp(CONSTANT, DATE_REGEXP))

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


}
