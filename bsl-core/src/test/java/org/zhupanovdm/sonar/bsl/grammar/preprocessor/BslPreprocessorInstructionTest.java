package org.zhupanovdm.sonar.bsl.grammar.preprocessor;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslPreprocessorInstruction;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.PREPROCESSOR_INSTRUCTION;
import static org.zhupanovdm.sonar.bsl.grammar.BslPreprocessorInstruction.*;

public class BslPreprocessorInstructionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void instruction() {
        assertThat(g.rule(PREPROCESSOR_INSTRUCTION))
                .matches("#if")
                .matches("#elsif")
                .matches("#endif")
                .matches("#region")
                .matches("#endregion");

        assertThat(g.rule(PREPROCESSOR_INSTRUCTION))
                .matches("# if")
                .notMatches("#\nif");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(PP_IF)).matches("#if").matches("#Если");
        assertThat(g.rule(PP_ELSIF)).matches("#elsif").matches("#ИначеЕсли");
        assertThat(g.rule(PP_END_IF)).matches("#endif").matches("#КонецЕсли");
        assertThat(g.rule(PP_REGION)).matches("#region").matches("#Область");
        assertThat(g.rule(PP_END_REGION)).matches("#endregion").matches("#КонецОбласти");

        assertThat(BslPreprocessorInstruction.values()).hasSize(5);
    }

}
