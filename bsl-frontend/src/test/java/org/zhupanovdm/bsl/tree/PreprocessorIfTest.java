package org.zhupanovdm.bsl.tree;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.module;

public class PreprocessorIfTest {

    @Test
    public void testIf() {
        PreprocessorIf pp = module("#If Foo Then ; #EndIf").getBody().get(0).as(PreprocessorIf.class);

        assertThat(pp.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(pp.getElsIfBranches()).isEmpty();
        assertThat(pp.getBody()).hasSize(1);
    }

    @Test
    public void testElsIf() {
        PreprocessorIf pp = module(
                "#If Foo0 Then\n" +
                        "#ElsIf Foo1 Then ;\n" +
                        "#ElsIf Foo2 Then ;\n" +
                        "#EndIf").getBody().get(0).as(PreprocessorIf.class);

        PreprocessorElsif elsIfBranch1 = pp.getElsIfBranches().get(0);
        assertThat(elsIfBranch1.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo1");
        assertThat(elsIfBranch1.getBody()).hasSize(1);

        PreprocessorElsif elsIfBranch2 = pp.getElsIfBranches().get(1);
        assertThat(elsIfBranch2.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo2");
        assertThat(elsIfBranch2.getBody()).hasSize(1);
    }
}