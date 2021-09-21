package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class RaiseStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        RaiseStatement stmt = creator.raiseStmt(parse("Raise Foo", g.rule(BslGrammar.RAISE_STMT)));

        assertThat(stmt.getExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getBody()).isEmpty();
    }
}