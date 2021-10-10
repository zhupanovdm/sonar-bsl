package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.EMPTY_STMT;

public class EmptyStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        EmptyStatement stmt = creator.emptyStmt(parse(";", g.rule(BslGrammar.EMPTY_STMT)));

        assertThat(stmt.getType()).isEqualTo(EMPTY_STMT);
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(1);
    }
}