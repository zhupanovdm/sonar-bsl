package org.zhupanovdm.bsl.api;

public enum BslPreprocessor implements BslWord {
    PP_IF( "Если"),
    PP_ELSIF("ИначеЕсли"),
    PP_END_IF("КонецЕсли"),
    PP_REGION("Область"),
    PP_END_REGION("КонецОбласти");

    private final String valueAlt;

    BslPreprocessor(String valueAlt) {
        this.valueAlt = valueAlt;
    }

    @Override
    public String getValue() {
        return BslWord.toValue(this).substring(2);
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
