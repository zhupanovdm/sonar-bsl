package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CompoundStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.COMPOUND_STATEMENT))
                .matches("a = 1")
                .matches("a = 1; b()")
                .matches("a = 1; b();")
                .matches(";")
                .matches(";;;")
                .notMatches("");
    }

}
