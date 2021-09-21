package org.zhupanovdm.bsl.tree.definition;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.api.BslDirective;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.PrimitiveNumber;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class CallableDefinitionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void function() {
        CallableDefinition stmt = creator.callableDef(parse("Function Foo() ; EndFunction", g.rule(BslGrammar.FUNC_DEF)));

        assertThat(stmt.getType()).isEqualTo(CallableDefinition.Type.FUNCTION);
        assertThat(stmt.getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getParameters()).isEmpty();

        assertThat(stmt.getDirective()).isNull();
        assertThat(stmt.getAsync()).isNull();
        assertThat(stmt.getExport()).isNull();

        assertThat(stmt.getBody()).hasSize(1);
    }

    @Test
    public void procedure() {
        CallableDefinition stmt = creator.callableDef(parse("Procedure Foo() ; EndProcedure", g.rule(BslGrammar.PROC_DEF)));

        assertThat(stmt.getType()).isEqualTo(CallableDefinition.Type.PROCEDURE);
        assertThat(stmt.getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getParameters()).isEmpty();

        assertThat(stmt.getDirective()).isNull();
        assertThat(stmt.getAsync()).isNull();
        assertThat(stmt.getExport()).isNull();

        assertThat(stmt.getBody()).hasSize(1);
    }

    @Test
    public void async() {
        assertThat(creator.callableDef(parse("Async Function Foo() EndFunction", g.rule(BslGrammar.FUNC_DEF)))
                .getAsync()).isNotNull();

        assertThat(creator.callableDef(parse("Async Procedure Foo() EndProcedure", g.rule(BslGrammar.PROC_DEF)))
                .getAsync()).isNotNull();
    }

    @Test
    public void export() {
        assertThat(creator.callableDef(parse("Function Foo() Export EndFunction", g.rule(BslGrammar.FUNC_DEF)))
                .getExport()).isNotNull();

        assertThat(creator.callableDef(parse("Procedure Foo() Export EndProcedure", g.rule(BslGrammar.PROC_DEF)))
                .getExport()).isNotNull();
    }

    @Test
    public void directive() {
        assertThat(creator.callableDef(parse("&AtServer Function Foo() EndFunction", g.rule(BslGrammar.FUNC_DEF)))
                .getDirective().getValue()).isEqualTo(BslDirective.AT_SERVER);

        assertThat(creator.callableDef(parse("&AtServer Procedure Foo() EndProcedure", g.rule(BslGrammar.PROC_DEF)))
                .getDirective().getValue()).isEqualTo(BslDirective.AT_SERVER);
    }

    @Test
    public void params() {
        List<Parameter> params = creator.callableDef(parse("Function Foo(A, Val B = 0) EndFunction", g.rule(BslGrammar.FUNC_DEF))).getParameters();

        Parameter param1 = params.get(0);
        assertThat(param1.getIdentifier().getValue()).isEqualTo("A");
        assertThat(param1.getVal()).isNull();
        assertThat(param1.getDefaultValue()).isNull();

        Parameter param2 = params.get(1);
        assertThat(param2.getIdentifier().getValue()).isEqualTo("B");
        assertThat(param2.getVal()).isNotNull();
        assertThat(param2.getDefaultValue().as(PrimitiveNumber.class).getValue()).isEqualTo("0");
    }

}