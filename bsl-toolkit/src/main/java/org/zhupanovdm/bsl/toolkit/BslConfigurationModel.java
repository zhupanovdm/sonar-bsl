package org.zhupanovdm.bsl.toolkit;

import com.sonar.sslr.impl.Parser;
import org.sonar.colorizer.CDocTokenizer;
import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.StringTokenizer;
import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.grammar.BslKeyword;
import org.zhupanovdm.bsl.toolkit.tokenizer.BslDirectiveTokenizer;

import java.nio.charset.Charset;
import java.util.*;

public class BslConfigurationModel extends AbstractConfigurationModel {

    private final Charset charset;

    public BslConfigurationModel(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Parser<LexerlessGrammar> doGetParser() {
        return BslParser.create(charset);
    }

    @Override
    public List<Tokenizer> doGetTokenizers() {
        KeywordsTokenizer keywordsTokenizer = new KeywordsTokenizer(
                "<span class=\"k\">", "</span>", keywords(),
                "\\p{javaJavaIdentifierStart}++\\p{javaJavaIdentifierPart}*+");
        keywordsTokenizer.setCaseInsensitive(true);

        return Collections.unmodifiableList(Arrays.asList(
                new BslDirectiveTokenizer("<span class=\"a\">", "</span>"),
                new StringTokenizer("<span class=\"s\">", "</span>"),
                new CDocTokenizer("<span class=\"cd\">", "</span>"),
                keywordsTokenizer
        ));
    }

    @Override
    public List<ConfigurationProperty> getProperties() {
        return Collections.emptyList();
    }

    public static Set<String> keywords() {
        Set<String> keywords = new HashSet<>();
        BslKeyword[] words = BslKeyword.values();
        for (BslKeyword word : words) {
            keywords.add(word.getValue().toUpperCase());
            if (word.getValueAlt() != null) {
                keywords.add(word.getValueAlt().toUpperCase());
            }
        }
        return keywords;
    }

}
