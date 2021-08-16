package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.TokenType;
import org.zhupanovdm.sonar.utils.BilingualUtils;

public interface BilingualWord extends TokenType {

    String getValueRu();

    default String getRegexp() {
        return BilingualUtils.caseInsensitiveRegexp(getValue(), getValueRu());
    }

}
