package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.Trivia;
import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.zhupanovdm.bsl.tree.BslToken.Type.COMMENT;

@Data
public class BslTrivia {
    private final List<BslToken> tokens = new LinkedList<>();
    private final String value;

    public BslTrivia(Trivia trivia) {
        this.value = trivia.getToken().getOriginalValue();
        trivia.getTokens().forEach(t -> tokens.add(new BslToken(t, COMMENT)));
    }

    public List<BslToken> getTokens() {
        return Collections.unmodifiableList(tokens);
    }
}
