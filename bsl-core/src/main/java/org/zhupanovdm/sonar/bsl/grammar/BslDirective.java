package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.AstNode;

public enum BslDirective implements BslWord {
    AT_CLIENT("НаКлиенте"),
    AT_SERVER("НаСервере"),
    AT_SERVER_NO_CONTEXT("НаСервереБезКонтекста"),
    AT_CLIENT_AT_SERVER("НаКлиентеНаСервере"),
    AT_CLIENT_AT_SERVER_NO_CONTEXT("НаКлиентеНаСервереБезКонтекста");

    private final String valueRu;

    BslDirective(String valueRu) {
        this.valueRu = valueRu;
    }

    @Override
    public String getValue() {
        return BslWord.enumToValue(this);
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
