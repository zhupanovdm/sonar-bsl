package org.zhupanovdm.bsl.sonar;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.impl.Lexer;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonarsource.analyzer.commons.TokenLocation;
import org.zhupanovdm.bsl.grammar.BslKeyword;
import org.zhupanovdm.bsl.lexer.BslLexer;
import org.zhupanovdm.bsl.lexer.BslTokenType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BslLexScanner {

    private static final String NORMALIZED_CHARACTER_LITERAL = "$CHARS";
    private static final String NORMALIZED_NUMERIC_LITERAL = "$NUMBER";
    private static final Set<TokenType> KEYWORDS = Arrays.stream(BslKeyword.values()).collect(Collectors.toSet());

    private final SensorContext context;

    public BslLexScanner(SensorContext context) {
        this.context = context;
    }

    public void scan(InputFile inputFile, String contents) {
        Lexer lexer = BslLexer.create(inputFile.charset());

        NewHighlighting highlighting = context.newHighlighting();
        highlighting.onFile(inputFile);

        NewCpdTokens cpdTokens = context.newCpdTokens();
        cpdTokens.onFile(inputFile);

        List<Token> tokens = lexer.lex(contents);
        for (Token token : tokens) {
            TokenType tokenType = token.getType();

            if (!tokenType.equals(GenericTokenType.EOF)) {
                TokenLocation tokenLocation = location(token);
                cpdTokens.addToken(tokenLocation.startLine(), tokenLocation.startLineOffset(), tokenLocation.endLine(), tokenLocation.endLineOffset(), image(token));
            }

            if (tokenType.equals(BslTokenType.NUMERIC_LITERAL)) {
                highlight(highlighting, location(token), TypeOfText.CONSTANT);
            } else if (tokenType.equals(BslTokenType.DATE_LITERAL)) {
                highlight(highlighting, location(token), TypeOfText.CONSTANT);
            } else if (tokenType.equals(GenericTokenType.LITERAL)) {
                highlight(highlighting, location(token), TypeOfText.STRING);
            } else if (KEYWORDS.contains(tokenType)) {
                highlight(highlighting, location(token), TypeOfText.KEYWORD);
            }
            for (Trivia trivia : token.getTrivia()) {
                highlight(highlighting, location(trivia.getToken()), TypeOfText.COMMENT);
            }
        }

        highlighting.save();
        cpdTokens.save();
    }

    private static String image(Token token) {
        if (token.getType().equals(GenericTokenType.LITERAL)) {
            return NORMALIZED_CHARACTER_LITERAL;
        } else if (token.getType().equals(BslTokenType.NUMERIC_LITERAL)) {
            return NORMALIZED_NUMERIC_LITERAL;
        }
        return token.getValue();
    }

    private static void highlight(NewHighlighting highlighting, TokenLocation tokenLocation, TypeOfText typeOfText) {
        highlighting.highlight(tokenLocation.startLine(), tokenLocation.startLineOffset(), tokenLocation.endLine(), tokenLocation.endLineOffset(), typeOfText);
    }

    private static TokenLocation location(Token token) {
        return new TokenLocation(token.getLine(), token.getColumn(), token.getOriginalValue());
    }

}
