package org.zhupanovdm.bsl.api;

public enum BslDirective implements BslWord {
    AT_CLIENT("НаКлиенте"),
    AT_SERVER("НаСервере"),
    AT_SERVER_NO_CONTEXT("НаСервереБезКонтекста"),
    AT_CLIENT_AT_SERVER("НаКлиентеНаСервере"),
    AT_CLIENT_AT_SERVER_NO_CONTEXT("НаКлиентеНаСервереБезКонтекста");

    private final String valueAlt;

    BslDirective(String valueAlt) {
        this.valueAlt = valueAlt;
    }

    @Override
    public String getValue() {
        return BslWord.toValue(this);
    }

    @Override
    public String getValueAlt() {
        return valueAlt;
    }

    @Override
    public String getName() {
        return name();
    }

}
