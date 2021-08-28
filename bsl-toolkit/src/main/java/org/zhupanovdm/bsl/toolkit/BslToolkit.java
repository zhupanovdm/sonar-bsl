package org.zhupanovdm.bsl.toolkit;

import org.sonar.sslr.toolkit.Toolkit;

import java.nio.charset.Charset;

public final class BslToolkit {

    private BslToolkit() {
    }

    public static void main(String[] args) {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SSDK");

        BslConfigurationModel configurationModel = new BslConfigurationModel(Charset.defaultCharset());
        Toolkit toolkit = new Toolkit("SSLR BSL Toolkit", configurationModel);
        toolkit.run();
    }

}