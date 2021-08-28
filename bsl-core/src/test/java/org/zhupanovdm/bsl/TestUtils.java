package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import org.assertj.core.util.Files;

import java.io.File;
import java.nio.charset.Charset;

public class TestUtils {

    private static final String RESOURCES = "src/test/resources/";

    public static String resource(String fileName) {
        return Files.contentOf(new File(RESOURCES, fileName), Charset.defaultCharset());
    }

    public static AstNode parse(String fileName) {
        return BslParser.create(Charset.defaultCharset())
                .parse(new File(RESOURCES, fileName));
    }

}