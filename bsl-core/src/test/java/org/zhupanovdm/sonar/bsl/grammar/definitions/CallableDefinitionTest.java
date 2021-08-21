package org.zhupanovdm.sonar.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.*;

public class CallableDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void function() {
        assertThat(g.rule(FUNC_DEFINITION))
                .matches("function a() export endfunction")
                .matches("&AtServer function a() endfunction")
                .matches("&AtServer\nfunction\na()\nfoo()\nendfunction")
                .notMatches("function a() export endfunction;");
    }

    @Test
    public void procedure() {
        assertThat(g.rule(PROC_DEFINITION))
                .matches("procedure a() export endprocedure")
                .matches("&AtServer procedure a() endprocedure")
                .matches("&AtServer\nprocedure\na()\nfoo()\nendprocedure")
                .notMatches("procedure a() export endprocedure;");
    }

    @Test
    public void signature() {
        assertThat(g.rule(SIGNATURE))
                .matches("a() export")
                .matches("a\n()\nexport")
                .matches("a()");
    }

    @Test
    public void parameters() {
        assertThat(g.rule(PARAMETER_LIST))
                .matches("")
                .matches("a")
                .matches("a, b, c")
                .matches("\na\n,\nb,\nc")
                .notMatches("a + b")
                .notMatches(",")
                .notMatches("a b");
    }

    @Test
    public void parameter() {
        assertThat(g.rule(PARAMETER))
                .matches("a")
                .matches("a = null")
                .matches("val a")
                .matches("val a = null")
                .matches("val\na\n=\nnull")
                .notMatches("")
                .notMatches("a = foo()");
    }

}
