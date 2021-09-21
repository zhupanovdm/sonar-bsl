package org.zhupanovdm.bsl.tree;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.statement.Statement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.module;

public class RegionTest {

    @Test
    public void testIf() {
        List<BslTree> body = module("#Region Foo ; #EndRegion").getBody();

        assertThat(body.get(0)).isInstanceOf(Statement.class);
        assertThat(body).hasSize(1);
    }
}
