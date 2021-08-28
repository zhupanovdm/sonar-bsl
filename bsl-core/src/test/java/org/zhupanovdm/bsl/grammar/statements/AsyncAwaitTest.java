package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.*;

public class AsyncAwaitTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void async() {
        assertThat(g.rule(FUNC_DEF))
                .matches("&AtClient Async Function a() EndFunction")
                .notMatches("&AtServer Async Function a() EndFunction")
                .notMatches("Async Function a() EndFunction");

        assertThat(g.rule(PROC_DEF))
                .matches("&AtClient Async Procedure a() EndProcedure")
                .notMatches("&AtServer Async Procedure a() EndProcedure")
                .notMatches("Async Procedure a() EndProcedure");
    }

    @Test
    public void await() {
        assertThat(g.rule(ASSIGNMENT_STATEMENT))
                .matches("a = Await b")
                .matches("a = Await b()");

        assertThat(g.rule(CALL_STATEMENT))
                .matches("Await b()");
    }

}
