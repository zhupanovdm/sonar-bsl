package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import lombok.Data;
import org.sonarsource.analyzer.commons.TokenLocation;

import java.util.LinkedList;
import java.util.List;

@Data
public class BslToken {
    private final Type type;
    private final int line;
    private final int column;
    private final String value;
    private final List<BslTrivia> comments = new LinkedList<>();

    public BslToken(Token token, Type type) {
        this.line   = token.getLine();
        this.column = token.getColumn();
        this.value  = token.getOriginalValue();
        this.type   = type;

        for (Trivia trivia : token.getTrivia()) {
            if (trivia.isComment()) {
                comments.add(new BslTrivia(trivia));
            }
        }
    }

    public boolean is(Type...types) {
        for (Type t : types) {
            if (type == t) {
                return true;
            }
        }
        return false;
    }

    public TokenLocation getLocation() {
        return new TokenLocation(line, column, value);
    }

    public enum Type {
        KEYWORD,
        KEYWORD_SYNTACTIC,
        IDENTIFIER,
        PUNCTUATOR,
        NUMERIC,
        CONSTANT,
        DATE_LITERAL,
        STRING_LITERAL,
        PREPROCESSOR,
        COMMENT,
        EOF
    }
}