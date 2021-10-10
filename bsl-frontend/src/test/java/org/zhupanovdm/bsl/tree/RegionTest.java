package org.zhupanovdm.bsl.tree;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.module;

public class RegionTest {
    @Test
    public void testIf() {
        ModuleRoot module = module("#Region Foo ; #EndRegion");
        List<BslTree> body = module.getBody();

        assertThat(body.get(0)).isInstanceOf(BslTree.class);
        assertThat(body).hasSize(1);
        assertThat(module.getTokens()).hasSize(6);
    }
}
