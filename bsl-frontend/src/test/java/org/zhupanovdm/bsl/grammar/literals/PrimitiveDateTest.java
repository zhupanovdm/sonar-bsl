package org.zhupanovdm.bsl.grammar.literals;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.DATE;

public class PrimitiveDateTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(DATE))
                .matches("'20210807224656'")
                .matches("'00010101000000'")
                .matches("'20210807'")
                .matches("'00010101'")
                .matches("'2021.08.07 22:46:56'")
                .matches("'0001.01.01 00:00:00'")
                .matches("'2021.08.07'")
                .matches("'0001.01.01'")
                .notMatches("'202108072246'")
                .notMatches("'202108'")
                .notMatches("'2021.08.07 22:46'")
                .notMatches("'2021.08'")
                .notMatches("''")
                .notMatches("' 20210807'");
    }

}
