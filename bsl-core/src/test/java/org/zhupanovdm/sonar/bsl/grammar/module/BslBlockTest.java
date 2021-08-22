package org.zhupanovdm.sonar.bsl.grammar.module;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.BLOCK;

public class BslBlockTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void program() {
        assertThat(g.rule(BLOCK))
                .matches("a = b()")
                .matches("a = b(); c()")
                .notMatches("a")
                .notMatches("1 + 1")
                .notMatches("var a;")
                .notMatches("function a() endfunction")
                .notMatches("");
    }

    @Test
    public void preprocessor() {
        assertThat(g.rule(BLOCK))
                .matches("#if server then #endif")
                .matches("#if server then ; #endif")
                .matches(
                        "#if server then\n" +
                        "#region a #endregion\n" +
                        "#elsif client then\n" +
                        "#region b #endregion\n" +
                        "#endif")
                .matches(
                        ";\n" +
                        "#if server then\n" +
                        ";\n" +
                        "#if server then\n" +
                        ";\n" +
                        "#elsif client then\n" +
                        ";\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        "#elsif client then\n" +
                        ";\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        ";");
    }

    @Test
    public void region() {
        assertThat(g.rule(BLOCK))
                .matches("#region a #endregion")
                .matches("#region a a=b; #endregion")
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
