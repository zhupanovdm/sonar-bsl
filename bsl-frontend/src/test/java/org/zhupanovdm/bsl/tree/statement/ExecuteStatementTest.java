package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.EXECUTE_STMT;

public class ExecuteStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ExecuteStatement stmt = creator.executeStmt(parse("Execute(Foo)", g.rule(BslGrammar.EXECUTE_STMT)));

        assertThat(stmt.getType()).isEqualTo(EXECUTE_STMT);
        assertThat(stmt.getExpression().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(3);
    }
}