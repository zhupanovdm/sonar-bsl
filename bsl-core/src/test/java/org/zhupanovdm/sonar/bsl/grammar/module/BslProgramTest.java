package org.zhupanovdm.sonar.bsl.grammar.module;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.PROGRAM;

public class BslProgramTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void block() {
        assertThat(g.rule(PROGRAM))
                .matches("var a; function b() endfunction a = b()")
                .matches("var a; var b;")
                .matches("function b() endfunction procedure b() endprocedure")
                .matches("a = b()")
                .notMatches("a = b(); var a;")
                .notMatches("a = b(); function b() endfunction")
                .notMatches("function b() endfunction var a;")
                .notMatches("");
    }

    @Test
    public void preprocessor() {
        assertThat(g.rule(PROGRAM))
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
        assertThat(g.rule(PROGRAM))
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
