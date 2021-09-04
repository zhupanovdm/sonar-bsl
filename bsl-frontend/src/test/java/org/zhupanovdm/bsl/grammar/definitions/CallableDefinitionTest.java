package org.zhupanovdm.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.*;

public class CallableDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void function() {
        assertThat(g.rule(FUNC_DEF))
                .matches("Function a() EndFunction")
                .matches("&AtServer Function a() EndFunction")
                .notMatches("Function a() EndFunction;");
    }

    @Test
    public void procedure() {
        assertThat(g.rule(PROC_DEF))
                .matches("Procedure a() EndProcedure")
                .matches("&AtServer Procedure a() EndProcedure")
                .notMatches("Procedure a() EndProcedure;");
    }

    @Test
    public void signature() {
        assertThat(g.rule(SIGNATURE))
                .matches("a()")
                .matches("a() Export")
                .matches("a(a)")
                .matches("a(a, b)")
                .notMatches("a(a b)")
                .notMatches("a(,)");
    }

    @Test
    public void parameter() {
        assertThat(g.rule(PARAMETER))
                .matches("a")
                .matches("a = Null")
                .matches("Val a")
                .matches("a = 1")
                .notMatches("a = 1 + 1")
                .notMatches("");
    }

}
