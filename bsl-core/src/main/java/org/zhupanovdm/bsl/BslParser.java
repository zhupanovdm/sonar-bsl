package org.zhupanovdm.bsl;

import com.sonar.sslr.impl.Parser;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

import java.nio.charset.Charset;

public final class BslParser {

    private BslParser() {
    }

    public static Parser<LexerlessGrammar> create(Charset charset) {
        return new ParserAdapter<>(charset, BslGrammar.create());
    }

}
