package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.*;

public class AsyncAwaitTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void async() {
        assertThat(g.rule(FUNC_DEF)).matches("Async Function a() EndFunction");
        assertThat(g.rule(PROC_DEF)).matches("Async Procedure a() EndProcedure");
    }

    @Test
    public void await() {
        assertThat(g.rule(EXPRESSION))
                .matches("Await a")
                .matches("Await (Await b)")
                .matches("Await b AND Await b")
                .matches("Await b()")
                .notMatches("Await Await b");

        assertThat(g.rule(CALL_STMT))
                .matches("Await b()")
                .matches("Await a.b()");
    }

}
