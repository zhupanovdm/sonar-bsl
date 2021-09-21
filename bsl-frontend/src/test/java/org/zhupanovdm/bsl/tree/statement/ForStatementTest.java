package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.PrimitiveNumber;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class ForStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ForStatement stmt = creator.forStmt(parse("For Foo = 0 To Bar Do ; EndDo", g.rule(BslGrammar.FOR_STMT)));

        assertThat(stmt.getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getInit().as(PrimitiveNumber.class).getValue()).isEqualTo("0");
        assertThat(stmt.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(stmt.getBody()).hasSize(1);
    }
}