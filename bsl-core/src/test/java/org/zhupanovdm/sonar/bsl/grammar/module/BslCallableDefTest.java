package org.zhupanovdm.sonar.bsl.grammar.module;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.CALLABLE_DEF;

public class BslCallableDefTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void block() {
        assertThat(g.rule(CALLABLE_DEF))
                .matches("function a() endfunction")
                .matches("procedure a() endprocedure")
                .matches(
                        "function a() endfunction\n" +
                        "procedure a() endprocedure")
                .notMatches("");
    }

    @Test
    public void preprocessor() {
        assertThat(g.rule(CALLABLE_DEF))
                .matches("#if server then #endif")
                .matches("#if server then function a() endfunction procedure a() endprocedure #endif")
                .matches(
                        "#if server then\n" +
                        "#region a #endregion\n" +
                        "#elsif client then\n" +
                        "#region b #endregion\n" +
                        "#endif")
                .matches(
                        "function a() endfunction\n" +
                        "#if server then\n" +
                        "function a() endfunction\n" +
                        "#if server then\n" +
                        "function a() endfunction\n" +
                        "#elsif client then\n" +
                        "function a() endfunction\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        "#elsif client then\n" +
                        "function a() endfunction\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        "function a() endfunction");
    }

}
