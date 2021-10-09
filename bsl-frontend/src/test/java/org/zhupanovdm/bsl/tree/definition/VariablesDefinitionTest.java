package org.zhupanovdm.bsl.tree.definition;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslDirective;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class VariablesDefinitionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void basic() {
        VariablesDefinition def = creator.variablesDef(parse("Var Foo;", g.rule(BslGrammar.VAR_DEF)));
        Variable variable = def.getBody().get(0).as(Variable.class);

        assertThat(def.getType()).isEqualTo(BslTree.Type.VAR_DEF);
        assertThat(def.getDirective().isPresent()).isFalse();
        assertThat(def.getBody()).hasSize(1);
        assertThat(def.getTokens()).hasSize(2);

        assertThat(variable.getType()).isEqualTo(BslTree.Type.VARIABLE);
        assertThat(variable.getName()).isEqualTo("Foo");
        assertThat(variable.isExport()).isFalse();
    }

    @Test
    public void several() {
        VariablesDefinition def = creator.variablesDef(parse("Var Foo, Bar Export;", g.rule(BslGrammar.VAR_DEF)));
        List<BslTree> body = def.getBody();

        assertThat(def.getDirective().isPresent()).isFalse();
        assertThat(def.getTokens()).hasSize(3);

        assertThat(body.get(0).as(Variable.class).getName()).isEqualTo("Foo");
        assertThat(body.get(0).as(Variable.class).isExport()).isFalse();
        assertThat(body.get(0).getTokens()).hasSize(1);

        assertThat(body.get(1).as(Variable.class).getName()).isEqualTo("Bar");
        assertThat(body.get(1).as(Variable.class).isExport()).isTrue();
        assertThat(body.get(1).getTokens()).hasSize(2);
    }

    @Test
    public void directive() {
        VariablesDefinition def = creator.variablesDef(parse("&AtClient Var Foo;", g.rule(BslGrammar.VAR_DEF)));

        assertThat(def.getDirective().get().getType()).isEqualTo(BslTree.Type.DIRECTIVE);
        assertThat(def.getDirective().get().getValue()).isEqualTo(BslDirective.AT_CLIENT);
    }
}