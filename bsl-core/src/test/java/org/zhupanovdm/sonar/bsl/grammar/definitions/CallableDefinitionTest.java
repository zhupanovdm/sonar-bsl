package org.zhupanovdm.sonar.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.*;

public class CallableDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void function() {
        assertThat(g.rule(FUNC_DEF))
                .matches("function a() endfunction")
                .matches("&AtServer function a() endfunction")
                .notMatches("function a() endfunction;");
    }

    @Test
    public void procedure() {
        assertThat(g.rule(PROC_DEF))
                .matches("procedure a() endprocedure")
                .matches("&AtServer procedure a() endprocedure")
                .notMatches("procedure a() endprocedure;");
    }

    @Test
    public void signature() {
        assertThat(g.rule(SIGNATURE))
                .matches("a()")
                .matches("a() export")
                .matches("a(a)")
                .matches("a(a, b)")
                .notMatches("a(a b)")
                .notMatches("a(,)");
    }

    @Test
    public void parameter() {
        assertThat(g.rule(PARAMETER))
                .matches("a")
                .matches("a = null")
                .matches("val a")
                .matches("a = 1")
                .notMatches("a = 1 + 1")
                .notMatches("");
    }

}
