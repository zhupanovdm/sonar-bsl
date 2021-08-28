package org.zhupanovdm.bsl.grammar.preprocessor;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslPreprocessor;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslPreprocessor.*;
import static org.zhupanovdm.bsl.BslGrammar.PREPROCESSOR_INSTRUCTION;

public class PreprocessorInstructionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void instruction() {
        assertThat(g.rule(PREPROCESSOR_INSTRUCTION))
                .matches("#If")
                .matches("#ElsIf")
                .matches("#EndIf")
                .matches("#Region")
                .matches("#EndRegion");

        assertThat(g.rule(PREPROCESSOR_INSTRUCTION))
                .matches("# If")
                .notMatches("#\nIf");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(PP_IF)).matches("#If").matches("#Если");
        assertThat(g.rule(PP_ELSIF)).matches("#ElsIf").matches("#ИначеЕсли");
        assertThat(g.rule(PP_END_IF)).matches("#EndIf").matches("#КонецЕсли");
        assertThat(g.rule(PP_REGION)).matches("#Region").matches("#Область");
        assertThat(g.rule(PP_END_REGION)).matches("#EndRegion").matches("#КонецОбласти");

        Assertions.assertThat(BslPreprocessor.values()).hasSize(5);
    }

}
