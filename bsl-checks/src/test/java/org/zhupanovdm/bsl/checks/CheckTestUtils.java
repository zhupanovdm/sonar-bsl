package org.zhupanovdm.bsl.checks;

import org.sonarsource.analyzer.commons.checks.verifier.FileContent;
import org.sonarsource.analyzer.commons.checks.verifier.MultiFileVerifier;
import org.zhupanovdm.bsl.Check;

import java.nio.file.Paths;

public class CheckTestUtils {
    private static final String RESOURCES = "src/test/resources/";

    public static FileContent resource(String fileName) {
        return new FileContent(Paths.get(RESOURCES, fileName));
    }

    public static MultiFileVerifier doCheck(String fileName, Check checkToTest) {
        VerifyingSampleModuleContext module = new VerifyingSampleModuleContext(new SampleModuleFile(fileName));
        module.subscribe(checkToTest, new VerifierCommentsSubscriber(module));
        module.scan();
        return module.getVerifier();
    }
}
