package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.PrimitiveExpression;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.FOR_STMT;
import static org.zhupanovdm.bsl.tree.BslTree.Type.VARIABLE;

public class ForStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ForStatement stmt = creator.forStmt(parse("For Foo = 0 To Bar Do ; EndDo", g.rule(BslGrammar.FOR_STMT)));

        assertThat(stmt.getType()).isEqualTo(FOR_STMT);
        assertThat(stmt.getVariable().getType()).isEqualTo(VARIABLE);
        assertThat(stmt.getVariable().getName()).isEqualTo("Foo");
        assertThat(stmt.getInit().as(PrimitiveExpression.class).getValue()).isEqualTo("0");
        assertThat(stmt.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(stmt.getBody()).hasSize(1);
        assertThat(stmt.getTokens()).hasSize(5);
    }
}