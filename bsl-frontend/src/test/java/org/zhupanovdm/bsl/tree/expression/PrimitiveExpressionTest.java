package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.grammar.BslGrammar.EXPRESSION;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

public class PrimitiveExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void undefined() {
        PrimitiveExpression expr = creator.expression(parse("Undefined", g.rule(EXPRESSION)).getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(UNDEFINED);
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void nullLiteral() {
        PrimitiveExpression expr = creator.expression(parse("Null", g.rule(EXPRESSION)).getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(NULL);
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void trueLiteral() {
        PrimitiveExpression expr = creator.expression(parse("True", g.rule(EXPRESSION)).getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(TRUE);
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void falseLiteral() {
        PrimitiveExpression expr = creator.expression(parse("False", g.rule(EXPRESSION)).getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(FALSE);
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void number() {
        PrimitiveExpression expr = creator.expression(parse("123", g.rule(EXPRESSION)).getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(NUMBER);
        assertThat(expr.getValue()).isEqualTo("123");
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void date() {
        PrimitiveExpression expr = creator.expression(parse("'00010101'", g.rule(EXPRESSION)).getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(DATE);
        assertThat(expr.getValue()).isEqualTo("00010101");
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void string() {
        AstNode tree = parse("\"Foo\"", g.rule(EXPRESSION));
        PrimitiveExpression expr = creator.expression(tree.getFirstChild()).as(PrimitiveExpression.class);

        assertThat(expr.getType()).isEqualTo(STRING);
        assertThat(expr.getValue()).isEqualTo("Foo");
        assertThat(expr.getBody()).hasSize(1);
        assertThat(expr.getTokens()).isEmpty();

        BslTree literal = expr.getBody().get(0);
        assertThat(literal.getType()).isEqualTo(STRING);
        assertThat(literal.as(PrimitiveExpression.class).getValue()).isEqualTo("Foo");
        assertThat(literal.getTokens()).hasSize(3);
    }

    @Test
    public void stringConcat() {
        AstNode tree = parse("\"B\" \"a\" \"r\" \"\"", g.rule(EXPRESSION));
        PrimitiveExpression expr = creator.expression(tree.getFirstChild()).as(PrimitiveExpression.class);
        List<BslTree> body = expr.getBody();

        assertThat(expr.getValue()).isEqualTo("Bar");
        assertThat(body.get(0).as(PrimitiveExpression.class).getValue()).isEqualTo("B");
        assertThat(body.get(1).as(PrimitiveExpression.class).getValue()).isEqualTo("a");
        assertThat(body.get(2).as(PrimitiveExpression.class).getValue()).isEqualTo("r");
        assertThat(body.get(3).as(PrimitiveExpression.class).getValue()).isEmpty();
        assertThat(body).hasSize(4);
    }

    @Test
    public void stringLb() {
        AstNode tree = parse("\"B\n\n|a\n|\n|r\" \"\"", g.rule(EXPRESSION));
        PrimitiveExpression expr = creator.expression(tree.getFirstChild()).as(PrimitiveExpression.class);
        List<BslTree> body = expr.getBody();

        assertThat(expr.getValue()).isEqualTo("B\na\n\nr");
        assertThat(body.get(0).as(PrimitiveExpression.class).getValue()).isEqualTo("B\na\n\nr");
        assertThat(body.get(1).as(PrimitiveExpression.class).getValue()).isEmpty();
        assertThat(body).hasSize(2);
    }
}