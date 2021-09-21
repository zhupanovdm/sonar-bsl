package org.zhupanovdm.bsl.tree;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.module.Module;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class BslTreeCreatorTest {
    @Test
    public void test() {
        Module module = moduleFile("samples/Sample02.bsl");
        assertThat(module).isNotNull();
    }
}
