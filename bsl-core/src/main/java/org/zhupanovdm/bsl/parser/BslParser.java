package org.zhupanovdm.bsl.parser;

import com.sonar.sslr.impl.Parser;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import java.nio.charset.Charset;

public final class BslParser {

    private BslParser() {
    }

    public static Parser<LexerlessGrammar> create(Charset charset) {
        return new ParserAdapter<>(charset, BslGrammar.createGrammar());
    }

}
