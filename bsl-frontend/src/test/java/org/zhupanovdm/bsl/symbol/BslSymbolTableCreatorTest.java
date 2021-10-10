package org.zhupanovdm.bsl.symbol;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.moduleFile;

public class BslSymbolTableCreatorTest {
    @Test
    public void test() {
        BslSymbolTableCreator subscriber = new BslSymbolTableCreator();

        BslTreePublisher.publish(moduleFile("/samples/metrics/Statements.bsl"), subscriber);

        assertThat(subscriber.getTable()).isNotNull();
    }
}