package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.*;

public class AsyncAwaitTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void async() {
        assertThat(g.rule(FUNC_DEF))
                .matches("&AtClient async function a() endfunction")
                .notMatches("&AtServer async function a() endfunction")
                .notMatches("async function a() endfunction");

        assertThat(g.rule(PROC_DEF))
                .matches("&AtClient async procedure a() endprocedure")
                .notMatches("&AtServer async procedure a() endprocedure")
                .notMatches("async procedure a() endprocedure");
    }

    @Test
    public void await() {
        assertThat(g.rule(ASSIGNMENT_STATEMENT))
                .matches("a = await b")
                .matches("a = await b()");

        assertThat(g.rule(CALL_STATEMENT))
                .matches("await b()");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(ASYNC)).matches("async").matches("асинх");
        assertThat(g.rule(AWAIT)).matches("await").matches("ждать");
    }

}
