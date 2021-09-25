package org.zhupanovdm.bsl.sonar;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader;
import org.zhupanovdm.bsl.checks.CheckList;

import static org.zhupanovdm.bsl.sonar.BslRuleRepository.RESOURCE_FOLDER;

public class BslProfile implements BuiltInQualityProfilesDefinition {
    public static final String PROFILE_NAME = "Sonar way";

    public static final String PROFILE_LOCATION = RESOURCE_FOLDER + "/Sonar_way_profile.json";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(PROFILE_NAME, Bsl.KEY);
        BuiltInQualityProfileJsonLoader.load(profile, CheckList.REPOSITORY_KEY, PROFILE_LOCATION);
        profile.done();
    }
}
