package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.RegexpChannelBuilder;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;
import org.zhupanovdm.bsl.grammar.BslPunctuator;

import java.nio.charset.Charset;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

public final class BslLexer {

    private BslLexer() {
    }

    public static Lexer create(Charset charset) {
        return Lexer.builder()
                .withCharset(charset)

                .withChannel(new BlackHoleChannel("\\s++"))

                // Comments
                .withChannel(commentRegexp("//[^\\n\\r]*+"))

                // Literals
                .withChannel(regexp(GenericTokenType.LITERAL, "\"(([^\"\\r\\n])|([\\r\\n]\\|))*+\""))
                .withChannel(RegexpChannelBuilder.regexp(BslTokenType.NUMERIC_LITERAL, "[+-]?[0-9]++(\\.([0-9]++)?+)?"))
                .withChannel(regexp(BslTokenType.DATE_LITERAL, "'(\\d{8}(\\d{6})?|\\d{4}\\.\\d{2}\\.\\d{2}( \\d{2}:\\d{2}:\\d{2})?)'"))
                .withChannel(new BslKeywordLiteralChannel(BslTokenType.BOOLEAN_LITERAL, "true", "false", "истина", "ложь"))
                .withChannel(regexp(BslTokenType.NULL_LITERAL, "(?i)null"))
                .withChannel(new BslKeywordLiteralChannel(BslTokenType.UNDEFINED_LITERAL, "undefined", "неопределено"))

                .withChannel(new BslIdentifierAndKeywordChannel())

                .withChannel(new PunctuatorChannel(BslPunctuator.values()))

                .withChannel(new UnknownCharacterChannel())
                .build();
    }

}
