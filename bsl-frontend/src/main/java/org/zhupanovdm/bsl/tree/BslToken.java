package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.Token;
import lombok.Data;

@Data
public class BslToken {
    private final int line;
    private final int column;
    private final String value;

    public BslToken(Token token) {
        this.line = token.getLine();
        this.column = token.getColumn();
        this.value = token.getValue();
    }
}