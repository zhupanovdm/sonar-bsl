package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class ForEachStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ForEachStatement stmt = creator.forEachStmt(parse("For each Foo In Bar Do ; EndDo", g.rule(BslGrammar.FOREACH_STMT)));

        assertThat(stmt.getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getCollection().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(stmt.getBody()).hasSize(1);
    }

}