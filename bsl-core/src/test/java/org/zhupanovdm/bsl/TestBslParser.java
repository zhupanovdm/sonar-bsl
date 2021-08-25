package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import org.zhupanovdm.bsl.parser.BslParser;

import java.io.File;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TestBslParser {

    public static AstNode parse(String fileName) {
        return BslParser.create(UTF_8).parse(new File("src/test/resources/samples/metrics/", fileName));
    }

}