package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.AstNode;

public enum BslPreprocessorSymbol implements BslWord {
    SERVER("Сервер"),
    AT_SERVER("НаСервере"),
    CLIENT("Клиент"),
    AT_CLIENT("НаКлиенте"),
    THIN_CLIENT("ТонкийКлиент"),
    WEB_CLIENT("ВебКлиент"),
    EXTERNAL_CONNECTION("ВнешнееСоединение"),
    THICK_CLIENT_MANAGED_APPLICATION("ТолстыйКлиентУправляемоеПриложение"),
    THICK_CLIENT_ORDINARY_APPLICATION("ТолстыйКлиентОбычноеПриложение"),
    MOBILE_APP_CLIENT("МобильноеПриложениеКлиент"),
    MOBILE_APP_SERVER("МобильноеПриложениеСервер");

    private final String valueRu;

    BslPreprocessorSymbol(String valueRu) {
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
