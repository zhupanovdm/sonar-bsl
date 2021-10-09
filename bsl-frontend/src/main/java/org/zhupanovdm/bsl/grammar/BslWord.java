package org.zhupanovdm.bsl.grammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;

public interface BslWord extends TokenType, GrammarRuleKey {
    String getValueAlt();

    @Override
    default boolean hasToBeSkippedFromAst(AstNode node) {
        return false;
    }

    static <E extends Enum<E>> String toValue(Enum<E> e) {
        return e.name().replaceAll("_", "");
    }
}
