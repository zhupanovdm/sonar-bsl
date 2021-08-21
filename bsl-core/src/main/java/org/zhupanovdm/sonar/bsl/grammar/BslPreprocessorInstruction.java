package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.AstNode;

public enum BslPreprocessorInstruction implements BslWord {
    IF("Если"),
    ELS_IF("ИначеЕсли"),
    END_IF("КонецЕсли"),
    REGION("Область"),
    END_REGION("КонецОбласти");

    private final String valueRu;

    BslPreprocessorInstruction(String valueRu) {
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
