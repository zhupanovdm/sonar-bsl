package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import org.sonarsource.analyzer.commons.checks.verifier.FileContent;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class TestUtils {

    private static final String RESOURCES = "src/test/resources/";

    public static String resource(String fileName) {
        return new FileContent(Paths.get(RESOURCES, fileName)).getContent();
    }

    public static AstNode parse(String fileName) {
        return BslParser.create(Charset.defaultCharset())
                .parse(new File(RESOURCES, fileName));
    }

}