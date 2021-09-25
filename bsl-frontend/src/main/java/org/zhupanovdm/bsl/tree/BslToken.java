package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.Trivia;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class BslToken {
    private final TokenType type;
    private final int line;
    private final int column;
    private final String value;
    private final List<BslTrivia> comments = new LinkedList<>();

    public BslToken(Token token) {
        this.line   = token.getLine();
        this.column = token.getColumn();
        this.value  = token.getValue();
        this.type   = token.getType();

        for (Trivia trivia : token.getTrivia()) {
            if (trivia.isComment()) {
                comments.add(new BslTrivia(trivia));
            }
        }
    }
}