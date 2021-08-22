package org.zhupanovdm.bsl.grammar;

import com.sonar.sslr.api.AstNode;

public enum BslPreprocessorInstruction implements BslWord {
    PP_IF("if", "Если"),
    PP_ELSIF("elsif", "ИначеЕсли"),
    PP_END_IF("endif", "КонецЕсли"),
    PP_REGION("region", "Область"),
    PP_END_REGION("endregion", "КонецОбласти");

    private final String value;
    private final String valueRu;

    BslPreprocessorInstruction(String value, String valueRu) {
        this.value = value;
        this.valueRu = valueRu;
    }

    @Override
    public String getValue() {
        return value;
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
