package org.zhupanovdm.bsl.checks;

import org.sonarsource.analyzer.commons.checks.verifier.MultiFileVerifier;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.Check;
import org.zhupanovdm.bsl.Issue;
import org.zhupanovdm.bsl.ModuleFile;
import org.zhupanovdm.bsl.tree.*;
import org.zhupanovdm.bsl.tree.module.Module;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class TestUtils {
    private static final String RESOURCES = "src/test/resources/";

    public static MultiFileVerifier doCheck(String fileName, Check checkToTest) {
        ModuleFileToTest moduleFile = new ModuleFileToTest(fileName);
        BslTreePublisher publisher = new BslTreePublisher().subscribe(checkToTest, new CommentsSubscriber(moduleFile));
        publisher.init();
        publisher.scan(moduleFile);
        return moduleFile.getVerifier();
    }

    private static class CommentsSubscriber implements BslTreeSubscriber {
        private final Path path;
        private final MultiFileVerifier verifier;

        public CommentsSubscriber(ModuleFileToTest moduleFile) {
            this.path = moduleFile.getPath();
            this.verifier = moduleFile.getVerifier();
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

    private static class ModuleFileToTest implements ModuleFile {
        private final File file;
        private final String contents;
        private final Module module;
        private final MultiFileVerifier verifier;

        public ModuleFileToTest(String fileName) {
            this.file = new File(RESOURCES, fileName);
            try {
                this.contents = new String(Files.readAllBytes(getPath()));
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read file: " + fileName, e);
            }
            this.module = new BslTreeCreator().create(BslParser.create(getCharset()).parse(this.contents));
            this.verifier = MultiFileVerifier.create(getPath(), getCharset());
        }

        public MultiFileVerifier getVerifier() {
            return verifier;
        }

        public Path getPath() {
            return file.toPath();
        }

        @Override
        public Charset getCharset() {
            return Charset.defaultCharset();
        }

        @Override
        public String getContents() {
            return contents;
        }

        @Override
        public Module getEntry() {
            return module;
        }

        @Override
        public List<Issue> getIssues() {
            return Collections.emptyList();
        }

        @Override
        public void addIssue(Issue issue) {
            MultiFileVerifier.IssueBuilder builder = verifier.reportIssue(getPath(), issue.getMessage());
            issue.getLine().ifPresentOrElse(builder::onLine, builder::onFile);
        }
    }
}
