package org.zhupanovdm.bsl;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;

import static org.zhupanovdm.bsl.BslGrammar.MODULE;

public class SampleTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(MODULE))
                .matches(TestUtils.resource("/samples/Sample01.bsl"));
    }

}
