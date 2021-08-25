package org.zhupanovdm.bsl.grammar;

import com.sonar.sslr.api.AstNode;

public enum BslAsync implements BslWord {
    ASYNC("Асинх"),
    AWAIT("Ждать");

    private final String valueRu;

    BslAsync(String valueRu) {
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
