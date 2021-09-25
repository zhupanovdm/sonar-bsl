package org.zhupanovdm.bsl.sonar;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class BslPlugin implements Plugin {
    public static final String FILE_SUFFIXES_KEY = "sonar.bsl.file.suffixes";

    private static final String BSL_CATEGORY = "BSL";

    private static final String GENERAL = "General";

    @Override
    public void define(Context context) {
        context.addExtensions(
                PropertyDefinition.builder(FILE_SUFFIXES_KEY)
                        .index(10)
                        .name("File Suffixes")
                        .description("List of suffixes of BSL files to analyze.")
                        .multiValues(true)
                        .category(BSL_CATEGORY)
                        .subCategory(GENERAL)
                        .onQualifiers(Qualifiers.PROJECT)
                        .defaultValue(Bsl.FILE_SUFFIX)
                        .build(),

                Bsl.class,
                BslSensor.class,

                BslRuleRepository.class,
                BslProfile.class
        );
    }
}
