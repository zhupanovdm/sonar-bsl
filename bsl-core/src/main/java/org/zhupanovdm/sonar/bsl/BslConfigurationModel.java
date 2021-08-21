package org.zhupanovdm.sonar.bsl;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.impl.Parser;
import org.sonar.colorizer.*;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.zhupanovdm.sonar.bsl.grammar.BslKeyword;
import org.zhupanovdm.sonar.bsl.grammar.BslWord;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class BslConfigurationModel extends AbstractConfigurationModel {

    @SuppressWarnings("rawtypes")
    @Override
    public Parser doGetParser() {
        return BslParser.create(Charset.defaultCharset());
    }

    @Override
    public List<Tokenizer> doGetTokenizers() {
        return ImmutableList.of(
                new StringTokenizer("<span class=\"s\">", "</span>"),
                new KeywordsTokenizer("<span class=\"k\">", "</span>", BslWord.allValues(BslKeyword.values())));
    }

    @Override
    public List<ConfigurationProperty> getProperties() {
        return Collections.emptyList();
    }
}
