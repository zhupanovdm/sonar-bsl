package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslDirective.*;
import static org.zhupanovdm.bsl.grammar.BslGrammar.DIRECTIVE;

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

        assertThat(BslDirective.values()).hasSize(5);
    }

}
