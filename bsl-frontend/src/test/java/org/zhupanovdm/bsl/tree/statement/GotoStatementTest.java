package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class GotoStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        GotoStatement stmt = creator.gotoStmt(parse("Goto ~Label", g.rule(BslGrammar.GOTO_STMT)));

        assertThat(stmt.getLabel().getIdentifier().getValue()).isEqualTo("Label");
        assertThat(stmt.getBody()).isEmpty();
    }
}