package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.Rule;
import org.sonar.sslr.internal.matchers.AstCreator;
import org.sonar.sslr.internal.matchers.InputBuffer;
import org.sonar.sslr.internal.matchers.LocatedText;
import org.sonar.sslr.parser.*;
import org.sonarsource.analyzer.commons.checks.verifier.FileContent;
import org.zhupanovdm.bsl.tree.module.Module;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class TestUtils {
    private static final String RESOURCES = "src/test/resources/";

    public static String resource(String fileName) {
        return new FileContent(Paths.get(RESOURCES, fileName)).getContent();
    }

    public static AstNode parse(String src) {
        return BslParser.create(Charset.defaultCharset()).parse(src);
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
        return BslParser.create(Charset.defaultCharset()).parse(new File(RESOURCES, fileName));
    }

    public static Module module(String src) {
        return new BslTreeCreator().create(parse(src));
    }

    public static Module moduleFile(String fileName) {
        return new BslTreeCreator().create(parseFile(fileName));
    }
}