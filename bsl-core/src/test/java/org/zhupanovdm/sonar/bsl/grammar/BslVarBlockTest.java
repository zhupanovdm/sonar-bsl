package org.zhupanovdm.sonar.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.VAR_BLOCK;

public class BslVarBlockTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void block() {
        assertThat(g.rule(VAR_BLOCK))
                .matches("var a;")
                .matches("var a; var b;")
                .notMatches("");
    }

    @Test
    public void preprocessor() {
        assertThat(g.rule(VAR_BLOCK))
                .matches("#if server then #endif")
                .matches("#if server then var a; var b; #endif")
                .matches(
                        "var a;\n" +
                        "var b;\n" +
                        "#if server then\n" +
                        "\tvar c;\n" +
                        "\t#if server then\n" +
                        "\t\tvar d;\n" +
                        "\t#elsif client then\n" +
                        "\t\tvar e;\n" +
                        "\t\tvar f;\n" +
                        "\t#elsif client then\n" +
                        "\t#endif\n" +
                        "#elsif client then\n" +
                        "\tvar j;\n" +
                        "#elsif client then\n" +
                        "#endif\n" +
                        "var k;"
                );
    }

    @Test
    public void region() {
        assertThat(g.rule(VAR_BLOCK))
                .matches("#region a #endregion")
                .matches("#region a var a; var b; #endregion")
                .matches("#region a #region b #endregion #endregion")

        ;
    }

}