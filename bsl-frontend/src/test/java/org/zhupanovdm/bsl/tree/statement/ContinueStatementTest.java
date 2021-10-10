package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.CONTINUE_STMT;

public class ContinueStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ContinueStatement stmt = creator.continueStmt(parse("Continue", g.rule(BslGrammar.CONTINUE_STMT)));

        assertThat(stmt.getType()).isEqualTo(CONTINUE_STMT);
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(1);
    }
}