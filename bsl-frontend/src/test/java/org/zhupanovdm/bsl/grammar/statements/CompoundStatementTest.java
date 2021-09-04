package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.COMPOUND_STATEMENT;

public class CompoundStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(COMPOUND_STATEMENT))
                .matches("a = 1")
                .matches("a = 1; b()")
                .matches("a = 1; b();")
                .matches(";")
                .matches(";;;")
                .matches("a = b;; c = d")
                .notMatches("1 + 1 a + b")
                .notMatches("");
    }

}
