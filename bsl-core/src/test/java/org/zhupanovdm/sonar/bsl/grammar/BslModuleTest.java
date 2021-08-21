package org.zhupanovdm.sonar.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.MODULE;

public class BslModuleTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(MODULE))
                .matches("")
                .matches("var a; function b() endfunction a = b()")
                .matches("var a; var b;")
                .matches("function b() endfunction procedure b() endprocedure")
                .matches("a = b()")
                .notMatches("a = b(); var a;")
                .notMatches("a = b(); function b() endfunction")
                .notMatches("function b() endfunction var a;");
    }
}
