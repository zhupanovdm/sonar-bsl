package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.Rule;
import com.sonar.sslr.impl.Parser;
import org.sonar.sslr.internal.matchers.AstCreator;
import org.sonar.sslr.internal.matchers.InputBuffer;
import org.sonar.sslr.internal.matchers.LocatedText;
import org.sonar.sslr.parser.*;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParserTestUtils {
    private static final String RESOURCES = "src/test/resources/";
    private static final Parser<LexerlessGrammar> PARSER = BslParser.create(Charset.defaultCharset());

    public static String resource(String fileName) {
        Path path = Paths.get(RESOURCES, fileName);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new IllegalStateException("Can't read file " + path, e);
        }
    }

    public static AstNode parse(String src) {
        return PARSER.parse(src);
    }

    public static AstNode parse(String source, Rule rule) {
        LocatedText text = new LocatedText(null, source.toCharArray());
        ParseRunner parseRunner = new ParseRunner(rule);
        ParsingResult result = parseRunner.parse(source.toCharArray());
        if (result.isMatched()) {
            return AstCreator.create(result, text);
        } else {
            ParseError parseError = result.getParseError();
            InputBuffer inputBuffer = parseError.getInputBuffer();
            int line = inputBuffer.getPosition(parseError.getErrorIndex()).getLine();
            String message = new ParseErrorFormatter().format(parseError);
            throw new RecognitionException(line, message);
        }
    }

    public static AstNode parseFile(String fileName) {
        return PARSER.parse(resource(fileName));
    }

    public static ModuleRoot module(String src) {
        return new BslTreeCreator().create(parse(src));
    }

    public static ModuleRoot moduleFile(String fileName) {
        return new BslTreeCreator().create(parseFile(fileName));
    }
}