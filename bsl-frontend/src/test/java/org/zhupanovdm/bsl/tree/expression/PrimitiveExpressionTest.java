package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class PrimitiveExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void undefined() {
        PrimitiveUndefined stmt = creator.expression(parse("Undefined", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveUndefined.class);

        assertThat(stmt).isNotNull();
    }

    @Test
    public void nullLiteral() {
        PrimitiveNull stmt = creator.expression(parse("Null", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveNull.class);

        assertThat(stmt).isNotNull();
    }

    @Test
    public void trueLiteral() {
        PrimitiveTrue stmt = creator.expression(parse("True", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveTrue.class);

        assertThat(stmt).isNotNull();
    }

    @Test
    public void falseLiteral() {
        PrimitiveFalse stmt = creator.expression(parse("False", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveFalse.class);

        assertThat(stmt).isNotNull();
    }

    @Test
    public void number() {
        PrimitiveNumber stmt = creator.expression(parse("123", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveNumber.class);

        assertThat(stmt).isNotNull();
        assertThat(stmt.getValue()).isEqualTo("123");
    }

    @Test
    public void date() {
        PrimitiveDate stmt = creator.expression(parse("'00010101'", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveDate.class);

        assertThat(stmt).isNotNull();
        assertThat(stmt.getValue()).isEqualTo("00010101");
    }

    @Test
    public void str() {
        PrimitiveString stmt = creator.expression(parse("\"Foo\"", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PrimitiveString.class);

        assertThat(stmt).isNotNull();
        assertThat(stmt.getValue()).isEqualTo("Foo");
        assertThat(stmt.getBody()).hasSize(1);
        assertThat(stmt.getBody().get(0).as(StringLiteral.class).getValue()).isEqualTo("Foo");
    }

    @Test
    public void strConcat() {
        AstNode tree = parse("\"B\" \"a\" \"r\" \"\"", g.rule(BslGrammar.EXPRESSION));
        PrimitiveString stmt = creator.expression(tree.getFirstChild()).as(PrimitiveString.class);

        assertThat(stmt).isNotNull();
        assertThat(stmt.getValue()).isEqualTo("Bar");
        assertThat(stmt.getBody()).hasSize(4);
        assertThat(stmt.getBody().get(0).as(StringLiteral.class).getValue()).isEqualTo("B");
        assertThat(stmt.getBody().get(1).as(StringLiteral.class).getValue()).isEqualTo("a");
        assertThat(stmt.getBody().get(2).as(StringLiteral.class).getValue()).isEqualTo("r");
        assertThat(stmt.getBody().get(3).as(StringLiteral.class).getValue()).isEmpty();
    }

    @Test
    public void strLb() {
        AstNode tree = parse("\"B\n\n|a\n|\n|r\" \"\"", g.rule(BslGrammar.EXPRESSION));
        PrimitiveString stmt = creator.expression(tree.getFirstChild()).as(PrimitiveString.class);

        assertThat(stmt).isNotNull();
        assertThat(stmt.getValue()).isEqualTo("B\na\n\nr");
        assertThat(stmt.getBody()).hasSize(2);
        assertThat(stmt.getBody().get(0).as(StringLiteral.class).getValue()).isEqualTo("B\na\n\nr");
        assertThat(stmt.getBody().get(1).as(StringLiteral.class).getValue()).isEmpty();
    }
}