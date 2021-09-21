package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;
import org.zhupanovdm.bsl.grammar.BslPunctuator;

import java.nio.charset.Charset;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;
import static org.zhupanovdm.bsl.lexer.BslTokenType.DATE_LITERAL;
import static org.zhupanovdm.bsl.lexer.BslTokenType.NUMERIC_LITERAL;

public final class BslLexer {

    private BslLexer() {
    }

    public static Lexer create(Charset charset) {
        return Lexer.builder()
                .withCharset(charset)
                .withFailIfNoChannelToConsumeOneCharacter(true)
                .withChannel(new BlackHoleChannel("\\s++"))

                .withChannel(commentRegexp("//[^\\n\\r]*+"))

                // Literals
                .withChannel(regexp(GenericTokenType.LITERAL, "\\\"(?:[^\\\"\\r\\n]|\\\"{2}|(?:[\\r\\n]\\s*\\|))*\\\""))
                .withChannel(regexp(NUMERIC_LITERAL, "[0-9]+(?:\\.(?:[0-9]++)?+)?"))
                .withChannel(regexp(DATE_LITERAL, "'(?:\\d{8}(?:\\d{6})?|\\d{4}\\.\\d{2}\\.\\d{2}(?: \\d{2}:\\d{2}:\\d{2})?)'"))

                .withChannel(new BslIdentifierAndKeywordChannel())

                .withChannel(new PunctuatorChannel(BslPunctuator.values()))

                .withChannel(new UnknownCharacterChannel())
                .build();

    }

}
