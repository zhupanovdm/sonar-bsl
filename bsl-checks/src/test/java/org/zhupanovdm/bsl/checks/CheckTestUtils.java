package org.zhupanovdm.bsl.checks;

import org.sonarsource.analyzer.commons.checks.verifier.FileContent;
import org.sonarsource.analyzer.commons.checks.verifier.MultiFileVerifier;
import org.zhupanovdm.bsl.*;
import org.zhupanovdm.bsl.tree.*;

import java.nio.file.Paths;

public class CheckTestUtils {
    private static final String RESOURCES = "src/test/resources/";

    public static FileContent resource(String fileName) {
        return new FileContent(Paths.get(RESOURCES, fileName));
    }

    public static MultiFileVerifier doCheck(String fileName, Check checkToTest) {
        VerifyingSampleModuleContext context = new VerifyingSampleModuleContext(new SampleModuleFile(fileName));
        BslTreePublisher publisher = new BslTreePublisher().subscribe(checkToTest, new VerifierCommentsSubscriber(context));
        publisher.init();
        publisher.scan(context);
        return context.getVerifier();
    }
}
