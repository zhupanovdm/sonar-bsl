package org.zhupanovdm.bsl.grammar;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;
import org.zhupanovdm.bsl.api.BslDirective;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslDirective.*;
import static org.zhupanovdm.bsl.BslGrammar.DIRECTIVE;

public class DirectiveTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void directive() {
        assertThat(g.rule(DIRECTIVE))
                .matches("&AtClient")
                .matches("&AtServer")
                .matches("&AtServerNoContext")
                .matches("&AtClientAtServer")
                .matches("&AtClientAtServerNoContext");

        assertThat(g.rule(DIRECTIVE))
                .matches("& AtClient")
                .notMatches("&\nAtClient");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(AT_CLIENT)).matches("&AtClient").matches("&НаКлиенте");
        assertThat(g.rule(AT_SERVER)).matches("&AtServer").matches("&НаСервере");
        assertThat(g.rule(AT_SERVER_NO_CONTEXT)).matches("&AtServerNoContext").matches("&НаСервереБезКонтекста");
        assertThat(g.rule(AT_CLIENT_AT_SERVER)).matches("&AtClientAtServer").matches("&НаКлиентеНаСервере");
        assertThat(g.rule(AT_CLIENT_AT_SERVER_NO_CONTEXT)).matches("&AtClientAtServerNoContext").matches("&НаКлиентеНаСервереБезКонтекста");

        Assertions.assertThat(BslDirective.values()).hasSize(5);
    }

}
