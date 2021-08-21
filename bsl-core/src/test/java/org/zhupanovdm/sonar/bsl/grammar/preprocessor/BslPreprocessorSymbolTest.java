package org.zhupanovdm.sonar.bsl.grammar.preprocessor;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslPreprocessorSymbol;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.PREPROCESSOR_SYMBOL;
import static org.zhupanovdm.sonar.bsl.grammar.BslPreprocessorSymbol.*;

public class BslPreprocessorSymbolTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void symbol() {
        assertThat(g.rule(PREPROCESSOR_SYMBOL))
                .matches("Server")
                .matches("AtServer")
                .matches("Client")
                .matches("AtClient")
                .matches("ThinClient")
                .matches("WebClient")
                .matches("ExternalConnection")
                .matches("ThickClientManagedApplication")
                .matches("ThickClientOrdinaryApplication")
                .matches("MobileAppClient")
                .matches("MobileAppServer");

        assertThat(g.rule(PREPROCESSOR_SYMBOL))
                .notMatches("server1")
                .notMatches("servers");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(SERVER)).matches("Server").matches("Сервер");
        assertThat(g.rule(AT_SERVER)).matches("AtServer").matches("НаСервере");
        assertThat(g.rule(CLIENT)).matches("Client").matches("Клиент");
        assertThat(g.rule(AT_CLIENT)).matches("AtClient").matches("НаКлиенте");
        assertThat(g.rule(THIN_CLIENT)).matches("ThinClient").matches("ТонкийКлиент");
        assertThat(g.rule(WEB_CLIENT)).matches("WebClient").matches("ВебКлиент");
        assertThat(g.rule(EXTERNAL_CONNECTION)).matches("ExternalConnection").matches("ВнешнееСоединение");
        assertThat(g.rule(THICK_CLIENT_MANAGED_APPLICATION)).matches("ThickClientManagedApplication").matches("ТолстыйКлиентУправляемоеПриложение");
        assertThat(g.rule(THICK_CLIENT_ORDINARY_APPLICATION)).matches("ThickClientOrdinaryApplication").matches("ТолстыйКлиентОбычноеПриложение");
        assertThat(g.rule(MOBILE_APP_CLIENT)).matches("MobileAppClient").matches("МобильноеПриложениеКлиент");
        assertThat(g.rule(MOBILE_APP_SERVER)).matches("MobileAppServer").matches("МобильноеПриложениеСервер");

        assertThat(BslPreprocessorSymbol.values()).hasSize(11);
    }

}
