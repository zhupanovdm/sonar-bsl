package org.zhupanovdm.bsl.tree;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.moduleFile;
import static org.zhupanovdm.bsl.tree.BslTree.Type.MODULE;

public class BslTreeCreatorTest {
    @Test
    public void test() {
        ModuleRoot module = moduleFile("samples/Sample02.bsl");
        assertThat(module.getType()).isEqualTo(MODULE);
    }
}
