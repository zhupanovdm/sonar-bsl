package org.zhupanovdm.bsl.sonar;

import org.sonar.api.Plugin;

public class BslPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtensions(
                Bsl.class,
                BslSensor.class,

                BslRulesDefinition.class,
                BslProfile.class
        );
    }

}
