package org.zhupanovdm.bsl.tree.definition;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.api.BslDirective;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class VariablesDefinitionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void basic() {
        VariablesDefinition def = creator.variablesDef(parse("Var Foo, Bar Export;", g.rule(BslGrammar.VAR_DEF)));

        assertThat(def.getDirective()).isNull();

        assertThat(def.getBody().get(0).as(Variable.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(def.getBody().get(0).as(Variable.class).getExport()).isNull();

        assertThat(def.getBody().get(1).as(Variable.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(def.getBody().get(1).as(Variable.class).getExport()).isNotNull();
    }

    @Test
    public void directive() {
        VariablesDefinition def = creator.variablesDef(parse("&AtClient Var Foo;", g.rule(BslGrammar.VAR_DEF)));

        assertThat(def.getDirective().getValue()).isEqualTo(BslDirective.AT_CLIENT);
    }
}