package org.zhupanovdm.bsl;

import org.sonar.sslr.toolkit.Toolkit;

public final class BslToolkit {

    private BslToolkit() {
    }

    public static void main(String[] args) {
        //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SSDK");

        Toolkit toolkit = new Toolkit("SSLR BSL Toolkit", new BslConfigurationModel());
        toolkit.run();
    }

}