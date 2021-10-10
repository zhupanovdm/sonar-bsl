package org.zhupanovdm.bsl.tree;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.module;
import static org.zhupanovdm.bsl.tree.BslTree.Type.PREPROCESSOR;

public class PreprocessorIfTest {

    @Test
    public void testIf() {
        PreprocessorIf pp = module("#If Foo Then ; #EndIf").getBody().get(0).as(PreprocessorIf.class);

        assertThat(pp.getType()).isEqualTo(PREPROCESSOR);
        assertThat(pp.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(pp.getElsIfBranches()).isEmpty();
        assertThat(pp.getBody()).hasSize(1);
        assertThat(pp.getTokens()).hasSize(5);
    }

    @Test
    public void testElsIf() {
        PreprocessorIf pp = module(
                "#If Foo0 Then\n" +
                        "#ElsIf Foo1 Then ;\n" +
                        "#ElsIf Foo2 Then ;\n" +
                        "#EndIf").getBody().get(0).as(PreprocessorIf.class);

        PreprocessorElsif elsIfBranch1 = pp.getElsIfBranches().get(0);
        assertThat(elsIfBranch1.getType()).isEqualTo(PREPROCESSOR);
        assertThat(elsIfBranch1.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo1");
        assertThat(elsIfBranch1.getBody()).hasSize(1);
        assertThat(elsIfBranch1.getTokens()).hasSize(3);

        PreprocessorElsif elsIfBranch2 = pp.getElsIfBranches().get(1);
        assertThat(elsIfBranch2.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo2");
        assertThat(elsIfBranch2.getBody()).hasSize(1);
        assertThat(elsIfBranch1.getTokens()).hasSize(3);
    }
}