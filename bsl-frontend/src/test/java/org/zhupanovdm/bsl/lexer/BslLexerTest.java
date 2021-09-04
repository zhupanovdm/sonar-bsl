package org.zhupanovdm.bsl.lexer;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.impl.Lexer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.Charset;

import static com.sonar.sslr.test.lexer.LexerMatchers.hasComment;
import static com.sonar.sslr.test.lexer.LexerMatchers.hasToken;
import static org.hamcrest.MatcherAssert.assertThat;


public class BslLexerTest {

    private static Lexer lexer;

    @BeforeClass
    public static void init() {
        lexer = BslLexer.create(Charset.defaultCharset());
    }

    @Test
    public void inline_comment() {
        assertThat(lexer.lex("// My Comment \n new line"), hasComment("// My Comment "));
        assertThat(lexer.lex("//"), hasComment("//"));
    }

    @Test
    public void decimal_literal() {
        assertThat(lexer.lex("0"), hasToken("0", BslTokenType.NUMERIC_LITERAL));
        assertThat(lexer.lex("1239"), hasToken("1239", BslTokenType.NUMERIC_LITERAL));
        assertThat(lexer.lex("1239.0"), hasToken("1239.0", BslTokenType.NUMERIC_LITERAL));
    }

    @Test
    public void string_literal() {
        assertThat("empty", lexer.lex("\"\""), hasToken("\"\"", GenericTokenType.LITERAL));
        assertThat(lexer.lex("\"hello world\""), hasToken("\"hello world\"", GenericTokenType.LITERAL));
        assertThat(lexer.lex("\"hello\n|world\""), hasToken("\"hello\n|world\"", GenericTokenType.LITERAL));
    }

    @Test
    public void date_literal() {
        assertThat(lexer.lex("'00010101'"), hasToken("'00010101'", BslTokenType.DATE_LITERAL));
        assertThat(lexer.lex("'00010101000000'"), hasToken("'00010101000000'", BslTokenType.DATE_LITERAL));
        assertThat(lexer.lex("'0001.01.01 00:00:00'"), hasToken("'0001.01.01 00:00:00'", BslTokenType.DATE_LITERAL));
        assertThat(lexer.lex("'0001.01.01'"), hasToken("'0001.01.01'", BslTokenType.DATE_LITERAL));
    }

    @Test
    public void identifier() {
        assertThat(lexer.lex("_"), hasToken("_", GenericTokenType.IDENTIFIER));
        assertThat(lexer.lex("identifier"), hasToken("IDENTIFIER", GenericTokenType.IDENTIFIER));
        assertThat(lexer.lex("идентификатор"), hasToken("ИДЕНТИФИКАТОР", GenericTokenType.IDENTIFIER));
    }

}
