package org.zhupanovdm.bsl.tree;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BslTreeTest {
    private TestTreeNode tree;
    private TestTreeNode child1;
    private TestTreeNode child2;

    @Before
    public void setup() {
        tree = new TestTreeNode(null);
        child1 = new TestTreeNode(new TestTreeNode(tree));
        child2 = new TestTreeNode(new TestTreeNode(tree));
    }

    @Test
    public void checkChild() {
        assertThat(child1.isChildOf(tree)).isTrue();
        assertThat(child1.isChildOf(null)).isFalse();
        assertThat(child1.isChildOf(child2)).isFalse();
        assertThat(tree.isChildOf(child1)).isFalse();
    }

    @Test
    public void checkParent() {
        assertThat(tree.isParentOf(child1)).isTrue();
        assertThat(tree.isParentOf(null)).isFalse();
        assertThat(child1.isParentOf(tree)).isFalse();
        assertThat(child1.isParentOf(child2)).isFalse();
    }

    private static class TestTreeNode extends BslTree {
        public TestTreeNode(TestTreeNode parent) {
            super(parent, null);
        }

        @Override
        public void accept(BslTreeSubscriber subscriber) {
        }
    }
}