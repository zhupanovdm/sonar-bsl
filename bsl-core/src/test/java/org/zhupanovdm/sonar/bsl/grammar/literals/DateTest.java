package org.zhupanovdm.sonar.bsl.grammar.literals;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class DateTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.DATE))
                .matches("'20210807224656'")
                .matches("'20210807'")
                .matches("'2021.08.07 22:46:56'")
                .matches("'2021.08.07'")
                .notMatches("'202108072246'")
                .notMatches("'202108'")
                .notMatches("'2021.08.07 22:46'")
                .notMatches("'2021.08'")
                .notMatches("''")
                .notMatches("' 20210807'");
    }

}