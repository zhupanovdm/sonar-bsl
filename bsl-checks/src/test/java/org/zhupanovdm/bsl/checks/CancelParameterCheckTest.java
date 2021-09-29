package org.zhupanovdm.bsl.checks;

import org.junit.Before;
import org.junit.Test;

import static org.zhupanovdm.bsl.checks.TestUtils.doCheck;

public class CancelParameterCheckTest {
    private CancelParameterCheck check;

    @Before
    public void setup() {
        check = new CancelParameterCheck();
    }

    @Test
    public void test() {
        doCheck("checks/CancelParameter.bsl", check).assertOneOrMoreIssues();
    }
}