package org.zhupanovdm.bsl.utils;

import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.zhupanovdm.bsl.utils.StringUtils.caseInsensitiveBilingualRegexpString;

public final class GrammarUtils {
    private GrammarUtils() {
    }

    public static Object bilingual(LexerlessGrammarBuilder b, String value, String valueAlt) {
        return b.regexp(caseInsensitiveBilingualRegexpString(value, valueAlt));
    }
}
