package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.AstNode;
import org.sonar.sslr.grammar.GrammarRuleKey;

public enum BslCompilationDirective implements BilingualWord, GrammarRuleKey {
    AT_CLIENT("НаКлиенте"),
    AT_SERVER("НаСервере"),
    AT_SERVER_NO_CONTEXT("НаСервереБезКонтекста"),
    AT_CLIENT_AT_SERVER("НаКлиентеНаСервере"),
    AT_CLIENT_AT_SERVER_NO_CONTEXT("НаКлиентеНаСервереБезКонтекста");

    private final String valueRu;

    BslCompilationDirective(String valueRu) {
        this.valueRu = valueRu;
    }

    @Override
    public String getValue() {
        return name().replaceAll("_", "");
    }

    @Override
    public String getValueRu() {
        return valueRu;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
        return false;
    }
}
