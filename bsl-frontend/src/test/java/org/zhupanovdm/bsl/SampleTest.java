package org.zhupanovdm.bsl;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.resource;
import static org.zhupanovdm.bsl.grammar.BslGrammar.MODULE;

public class SampleTest {
    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(MODULE)).matches(resource("/samples/Sample01.bsl"));
    }
}
