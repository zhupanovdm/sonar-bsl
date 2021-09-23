package org.zhupanovdm.bsl.tree.definition;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslDirective;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.PrimitiveExpression;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.grammar.BslGrammar.FUNC_DEF;
import static org.zhupanovdm.bsl.grammar.BslGrammar.PROC_DEF;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

public class CallableDefinitionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void function() {
        CallableDefinition stmt = creator.callableDef(parse("Function Foo() ; EndFunction", g.rule(FUNC_DEF)), FUNCTION);

        assertThat(stmt.getDirective()).isNull();
        assertThat(stmt.isAsync()).isFalse();
        assertThat(stmt.getType()).isEqualTo(FUNCTION);
        assertThat(stmt.getName()).isEqualTo("Foo");
        assertThat(stmt.getParameters()).isEmpty();
        assertThat(stmt.isExport()).isFalse();
        assertThat(stmt.getBody()).hasSize(1);

        assertThat(stmt.getTokens()).hasSize(5);
    }

    @Test
    public void procedure() {
        CallableDefinition stmt = creator.callableDef(parse("Procedure Foo() ; EndProcedure", g.rule(PROC_DEF)), PROCEDURE);

        assertThat(stmt.getDirective()).isNull();
        assertThat(stmt.isAsync()).isFalse();
        assertThat(stmt.getType()).isEqualTo(PROCEDURE);
        assertThat(stmt.getName()).isEqualTo("Foo");
        assertThat(stmt.getParameters()).isEmpty();
        assertThat(stmt.isExport()).isFalse();
        assertThat(stmt.getBody()).hasSize(1);

        assertThat(stmt.getTokens()).hasSize(5);
    }

    @Test
    public void asyncFunction() {
        CallableDefinition stmt = creator.callableDef(parse("Async Function Foo() EndFunction", g.rule(FUNC_DEF)), FUNCTION);

        assertThat(stmt.isAsync()).isTrue();
        assertThat(stmt.getTokens()).hasSize(6);
    }

    @Test
    public void asyncProcedure() {
        CallableDefinition stmt = creator.callableDef(parse("Async Procedure Foo() EndProcedure", g.rule(PROC_DEF)), PROCEDURE);

        assertThat(stmt.isAsync()).isTrue();
        assertThat(stmt.getTokens()).hasSize(6);
    }

    @Test
    public void exportFunction() {
        CallableDefinition stmt = creator.callableDef(parse("Function Foo() Export EndFunction", g.rule(FUNC_DEF)), FUNCTION);

        assertThat(stmt.isExport()).isTrue();
        assertThat(stmt.getTokens()).hasSize(6);
    }

    @Test
    public void exportProcedure() {
        CallableDefinition stmt = creator.callableDef(parse("Procedure Foo() Export EndProcedure", g.rule(PROC_DEF)), PROCEDURE);

        assertThat(stmt.isExport()).isTrue();
        assertThat(stmt.getTokens()).hasSize(6);
    }

    @Test
    public void directiveFunction() {
        CallableDefinition stmt = creator.callableDef(parse("&AtServer Function Foo() EndFunction", g.rule(FUNC_DEF)), FUNCTION);

        assertThat(stmt.getDirective().getType()).isEqualTo(BslTree.Type.DIRECTIVE);
        assertThat(stmt.getDirective().getValue()).isEqualTo(BslDirective.AT_SERVER);
        assertThat(stmt.getTokens()).hasSize(5);
    }

    @Test
    public void directiveProcedure() {
        CallableDefinition stmt = creator.callableDef(parse("&AtServer Procedure Foo() EndProcedure", g.rule(PROC_DEF)), PROCEDURE);

        assertThat(stmt.getDirective().getType()).isEqualTo(BslTree.Type.DIRECTIVE);
        assertThat(stmt.getDirective().getValue()).isEqualTo(BslDirective.AT_SERVER);
        assertThat(stmt.getTokens()).hasSize(5);
    }

    @Test
    public void param() {
        CallableDefinition stmt = creator.callableDef(parse("Function Foo(A) EndFunction", g.rule(FUNC_DEF)), FUNCTION);
        Parameter param = stmt.getParameters().get(0);

        assertThat(param.getType()).isEqualTo(PARAMETER);
        assertThat(param.getName()).isEqualTo("A");
        assertThat(param.getTokens()).hasSize(1);
    }

    @Test
    public void params() {
        List<Parameter> params = creator.callableDef(parse("Function Foo(A, Val B = 0) EndFunction", g.rule(FUNC_DEF)), FUNCTION)
                .getParameters();

        Parameter param1 = params.get(0);
        assertThat(param1.getName()).isEqualTo("A");
        assertThat(param1.isVal()).isFalse();
        assertThat(param1.getDefaultValue()).isNull();

        Parameter param2 = params.get(1);
        assertThat(param2.getName()).isEqualTo("B");
        assertThat(param2.isVal()).isTrue();
        assertThat(param2.getDefaultValue().as(PrimitiveExpression.class).getValue()).isEqualTo("0");

        assertThat(param2.getTokens()).hasSize(3);
    }
}