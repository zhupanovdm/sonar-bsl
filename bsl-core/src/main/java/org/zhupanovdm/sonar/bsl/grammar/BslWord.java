package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.TokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;

public interface BslWord extends TokenType, GrammarRuleKey {

    String getValueRu();

    static String[] allValues(BslWord[] words) {
        String[] keywordsValue = new String[words.length * 2];
        for (int i = 0; i < words.length; i++) {
            keywordsValue[2 * i] = words[i].getValue();
            keywordsValue[2 * i + 1] = words[i].getValueRu();
        }
        return keywordsValue;
    }

    static <E extends Enum<E>> String enumToValue(Enum<E> e) {
        return e.name().replaceAll("_", "");
    }

}
