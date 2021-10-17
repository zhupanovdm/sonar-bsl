package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import lombok.Data;
import org.zhupanovdm.bsl.Position;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
public class BslToken {
    private final Type type;
    private final Position position;
    private final String value;
    private final List<BslTrivia> comments = new LinkedList<>();

    public BslToken(Token token, Type type) {
        this.position   = Position.fromToken(token);
        this.value      = token.getOriginalValue();
        this.type       = type;

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

    public List<BslTrivia> getComments() {
        return Collections.unmodifiableList(comments);
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