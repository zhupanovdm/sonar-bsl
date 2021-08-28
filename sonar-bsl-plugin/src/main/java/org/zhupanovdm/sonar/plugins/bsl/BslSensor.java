package org.zhupanovdm.sonar.plugins.bsl;

import com.sonar.sslr.api.*;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.Parser;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslWord;
import org.zhupanovdm.bsl.api.BslKeyword;
import org.zhupanovdm.bsl.lexer.BslLexer;
import org.zhupanovdm.bsl.metrics.ComplexityMetricsStub;
import org.zhupanovdm.bsl.metrics.LinesMetrics;
import org.zhupanovdm.bsl.metrics.ModuleMetrics;
import org.zhupanovdm.bsl.BslParser;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static org.sonar.api.batch.fs.InputFile.Type.MAIN;

public class BslSensor implements Sensor {

    private static final Logger LOG = Loggers.get(BslSensor.class);

    private static final String NORMALIZED_CHARACTER_LITERAL = "$CHARS";
    /*
    private static final Set<? extends BslWord> KEYWORDS = Sets.immutableEnumSet(Arrays.asList(BslKeyword.values()));
     */
    private static final Set<? extends BslWord> KEYWORDS = Collections.emptySet();

    private final FileLinesContextFactory fileLinesContextFactory;

    public BslSensor(FileLinesContextFactory fileLinesContextFactory) {
        this.fileLinesContextFactory = fileLinesContextFactory;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .name("BSL")
                .onlyOnFileType(MAIN)
                .onlyOnLanguage(Bsl.KEY);
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        FilePredicates predicates = fs.predicates();
        FilePredicate predicate = predicates.and(predicates.hasType(MAIN), predicates.hasLanguage(Bsl.KEY));

        List<InputFile> files = new LinkedList<>();
        fs.inputFiles(predicate).forEach(files::add);

        /*
        ProgressReport progress = new ProgressReport("Report about progress of the SonarSource BSL analyzer", SECONDS.toMillis(10));
        List<String> filenames = files.stream().map(InputFile::toString).collect(Collectors.toList());
        progress.start(filenames);
        for (InputFile file : files) {
            analyse(context, file);
            progress.nextFile();
        }
        progress.stop();
         */
    }

    private void analyse(SensorContext context, InputFile file) {
        String contents;
        try {
            contents = file.contents();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + file, e);
        }

        Parser<LexerlessGrammar> parser = BslParser.create(file.charset());
        try {
            saveMeasures(context, file, parser.parse(contents));
        } catch (RecognitionException e) {
            LOG.error("Unable to parse file: {}", file);
            LOG.error(e.getMessage());
        }

        highlight(context, file, contents);
    }

    private void saveMeasures(SensorContext context, InputFile file, AstNode tree) {
        LinesMetrics linesMetrics = new LinesMetrics(tree);
        ModuleMetrics moduleMetrics = new ModuleMetrics(tree);

        saveMeasure(context, file, CoreMetrics.NCLOC, linesMetrics.getLinesOfCode().size());
        saveMeasure(context, file, CoreMetrics.COMMENT_LINES, linesMetrics.getLinesOfComments().size());
        saveMeasure(context, file, CoreMetrics.FUNCTIONS, moduleMetrics.getNumberOfFunctions());
        saveMeasure(context, file, CoreMetrics.STATEMENTS, moduleMetrics.getNumberOfStatements());
        saveMeasure(context, file, CoreMetrics.EXECUTABLE_LINES_DATA, moduleMetrics.getExecutableLines());

        FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(file);
        linesMetrics.getLinesOfCode().forEach(line -> fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, 1));
        fileLinesContext.save();

        saveMeasure(context, file, CoreMetrics.COMPLEXITY, ComplexityMetricsStub.complexity(tree));
    }

    private void highlight(SensorContext context, InputFile file, String contents) {
        Lexer lexer = BslLexer.create(file.charset());

        NewHighlighting highlighting = context.newHighlighting();
        highlighting.onFile(file);

        NewCpdTokens cpdTokens = context.newCpdTokens();
        cpdTokens.onFile(file);

        for (Token token : lexer.lex(contents)) {
            TokenType tokenType = token.getType();

            if (!tokenType.equals(GenericTokenType.EOF)) {
                TokenLocation tokenLocation = new TokenLocation(token);
                cpdTokens.addToken(tokenLocation.startLine(), tokenLocation.startCharacter(), tokenLocation.endLine(), tokenLocation.endCharacter(), getTokenImage(token));
            }

            if (tokenType.equals(GenericTokenType.CONSTANT)) {
                highlightToken(highlighting, token, TypeOfText.CONSTANT);
            } else if (tokenType.equals(GenericTokenType.LITERAL)) {
                highlightToken(highlighting, token, TypeOfText.STRING);
            } else //noinspection SuspiciousMethodCalls
                if (KEYWORDS.contains(tokenType)) {
                highlightToken(highlighting, token, TypeOfText.KEYWORD);
            }
            for (Trivia trivia : token.getTrivia()) {
                highlightToken(highlighting, trivia.getToken(), TypeOfText.COMMENT);
            }
        }

        highlighting.save();
        cpdTokens.save();

    }

    private static String getTokenImage(Token token) {
        if (token.getType().equals(GenericTokenType.LITERAL))
            return NORMALIZED_CHARACTER_LITERAL;
        return token.getValue();
    }

    private static <T extends Serializable> void saveMeasure(SensorContext context, InputFile file, Metric<T> metric, T value) {
        context.<T>newMeasure().on(file).forMetric(metric).withValue(value).save();
    }

    private static void highlightToken(NewHighlighting highlighting, Token token, TypeOfText typeOfText) {
        TokenLocation tokenLocation = new TokenLocation(token);
        highlighting.highlight(tokenLocation.startLine(), tokenLocation.startCharacter(), tokenLocation.endLine(), tokenLocation.endCharacter(), typeOfText);
    }

}
