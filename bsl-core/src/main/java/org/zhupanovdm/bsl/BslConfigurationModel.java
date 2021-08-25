package org.zhupanovdm.bsl;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.impl.Parser;
import org.sonar.colorizer.*;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.zhupanovdm.bsl.grammar.BslKeyword;
import org.zhupanovdm.bsl.grammar.BslWord;
import org.zhupanovdm.bsl.parser.BslParser;

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
