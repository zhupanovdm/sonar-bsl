package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.BREAK_STMT;

public class BreakStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        BreakStatement stmt = creator.breakStmt(parse("Break", g.rule(BslGrammar.BREAK_STMT)));

        assertThat(stmt.getType()).isEqualTo(BREAK_STMT);
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(1);
    }
}