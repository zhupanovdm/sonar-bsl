package org.zhupanovdm.bsl.checks;

import org.sonarsource.analyzer.commons.checks.verifier.MultiFileVerifier;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.tree.*;
import org.zhupanovdm.bsl.tree.module.Module;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class TestUtils {
    private static final String RESOURCES = "src/test/resources/";

    public static MultiFileVerifier doCheck(String fileName, BslCheck checkToTest) {
        Charset charset = Charset.defaultCharset();
        File file = new File(RESOURCES, fileName);
        Path path = file.toPath();
        Module module = new BslTreeCreator().create(BslParser.create(charset).parse(file));

        MultiFileVerifier verifier = MultiFileVerifier.create(path, charset);
        checkToTest.setIssueConsumer((check, issue) -> {
            MultiFileVerifier.IssueBuilder builder = verifier.reportIssue(path, issue.message());
            if (issue.line() == null) {
                builder.onFile();
            } else {
                builder.onLine(issue.line());
            }
        });
        BslTreePublisher.publish(module, new CommentsSubscriber(path, verifier), checkToTest);
        return verifier;
    }

    private static class CommentsSubscriber implements BslTreeSubscriber {
        private final Path path;
        private final MultiFileVerifier verifier;

        public CommentsSubscriber(Path path, MultiFileVerifier verifier) {
            this.path = path;
            this.verifier = verifier;
        }

        @Override
        public void onEnterNode(BslTree node) {
            for (BslToken token : node.getTokens()) {
                for (BslTrivia comment : token.getComments()) {
                    BslToken t = comment.getTokens().get(0);
                    verifier.addComment(path, t.getLine(), t.getColumn(), t.getValue(), 2, 0);
                }
            }
        }
    }
}
