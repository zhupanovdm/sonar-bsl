package org.zhupanovdm.sonar.plugins.bsl;

import org.sonar.api.Plugin;

public class BslPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtensions(
                Bsl.class,
                BslSensor.class
        );
    }

}
