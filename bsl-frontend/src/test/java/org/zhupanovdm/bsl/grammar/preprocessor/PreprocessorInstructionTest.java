package org.zhupanovdm.bsl.grammar.preprocessor;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.*;

public class PreprocessorInstructionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(PP_IF)).matches("#If").matches("#Если");
        assertThat(g.rule(PP_ELSIF)).matches("#ElsIf").matches("#ИначеЕсли");
        assertThat(g.rule(PP_END_IF)).matches("#EndIf").matches("#КонецЕсли");
        assertThat(g.rule(REGION)).matches("#Region").matches("#Область");
        assertThat(g.rule(END_REGION)).matches("#EndRegion").matches("#КонецОбласти");

        assertThat(g.rule(PP_IF)).matches("# If").notMatches("#\nIf");
        assertThat(g.rule(PP_ELSIF)).matches("# ElsIf").notMatches("#\nElsIf");
        assertThat(g.rule(PP_END_IF)).matches("# EndIf").notMatches("#\nEndIf");
        assertThat(g.rule(REGION)).matches("# Region").notMatches("#\nRegion");
        assertThat(g.rule(END_REGION)).matches("# EndRegion").notMatches("#\nEndRegion");
    }
}
