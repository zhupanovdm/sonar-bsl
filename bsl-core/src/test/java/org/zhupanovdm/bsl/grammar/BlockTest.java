package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.BLOCK;

public class BlockTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void block() {
        assertThat(g.rule(BLOCK))
                .matches("Var a; Function b() EndFunction a = b()")
                .matches("Var a; Var b;")
                .matches("Function b() EndFunction Procedure b() EndProcedure")
                .matches("a = b()")
                .matches("a = b;; c = d")
                .notMatches("a = b c = d")
                .notMatches("");
    }

    @Test
    public void preprocessor() {
        assertThat(g.rule(BLOCK))
                .matches("#If Server Then #EndIf")
                .matches("Var v; v = 3 #If Server Then f() #EndIf g() #If Server Then m() #EndIf")
                .matches(
                        "#If Server then\n" +
                        "#Region a #EndRegion\n" +
                        "#ElsIf Client Then\n" +
                        "#Region b #EndRegion\n" +
                        "#EndIf")
                .matches(
                        "#If Server Then\n" +
                        "#If Server Then\n" +
                        "#ElsIf Client Then\n" +
                        "#ElsIf Client Then\n" +
                        "#EndIf\n" +
                        "#ElsIf Client Then\n" +
                        "#ElsIf Client Then\n" +
                        "#EndIf");
    }

    @Test
    public void region() {
        assertThat(g.rule(BLOCK))
                .matches("#Region a #EndRegion")
                .matches("#Region a ; #EndRegion")
                .matches("#Region a #Region b #EndRegion #EndRegion")
                .matches(
                        "#Region a\n" +
                        "#If Server Then\n" +
                        "#ElsIf Client Then\n" +
                        "#EndIf\n" +
                        "#EndRegion")
                .matches(
                        ";\n" +
                        "#Region b\n" +
                        ";\n" +
                        "#Region c\n" +
                        ";\n" +
                        "#EndRegion\n" +
                        ";\n" +
                        "#EndRegion\n" +
                        ";");
    }

}
