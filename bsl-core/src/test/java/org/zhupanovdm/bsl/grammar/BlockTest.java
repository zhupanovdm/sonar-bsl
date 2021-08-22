package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.BLOCK;

public class BlockTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void block() {
        assertThat(g.rule(BLOCK))
                .matches("var a; function b() endfunction a = b()")
                .matches("var a; var b;")
                .matches("function b() endfunction procedure b() endprocedure")
                .matches("a = b()")
                .notMatches("");
    }

    @Test
    public void preprocessor() {
        assertThat(g.rule(BLOCK))
                .matches("#if server then #endif")
                .matches("var v; v = 3 #if server then f() #endif g() #if server then m() #endif")
                .matches(
                        "#if server then\n" +
                        "#region a #endregion\n" +
                        "#elsif client then\n" +
                        "#region b #endregion\n" +
                        "#endif")
                .matches(
                        "#if server then\n" +
                        "#if server then\n" +
                        "#elsif client then\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        "#elsif client then\n" +
                        "#elsif client then\n" +
                        "#endif");
    }

    @Test
    public void region() {
        assertThat(g.rule(BLOCK))
                .matches("#region a #endregion")
                .matches("#region a ; #endregion")
                .matches("#region a #region b #endregion #endregion")
                .matches(
                        "#region a\n" +
                        "#if server then\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        "#endregion")
                .matches(
                        ";\n" +
                        "#region b\n" +
                        ";\n" +
                        "#region c\n" +
                        ";\n" +
                        "#endregion\n" +
                        ";\n" +
                        "#endregion\n" +
                        ";");
    }

}
