package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class CompoundStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.COMPOUND_STATEMENT))
                .matches("a = 1")
                .matches("a = 1; b()")
                .matches("a = 1; b();")
                .matches(";")
                .matches(";;;")
                .notMatches("");
    }

}
