package org.zhupanovdm.bsl.checks;

import org.junit.Before;
import org.junit.Test;

import static org.zhupanovdm.bsl.checks.TestUtils.doCheck;

public class EventHandlersCancelParameterCheckTest {
    private EventHandlersCancelParameterCheck check;

    @Before
    public void setup() {
        check = new EventHandlersCancelParameterCheck();
    }

    @Test
    public void test() {
        doCheck("checks/EventHandlersCancelParameter.bsl", check).assertOneOrMoreIssues();
    }
}