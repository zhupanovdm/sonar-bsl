package org.zhupanovdm.bsl.sonar;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader;
import org.zhupanovdm.bsl.checks.CheckList;

public class BslProfile implements BuiltInQualityProfilesDefinition {

    public static final String SONAR_WAY_PROFILE_PATH = "org/zhupanovdm/bsl/rules/Sonar_way_profile.json";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(CheckList.SONAR_WAY_PROFILE, Bsl.KEY);
        BuiltInQualityProfileJsonLoader.load(profile, CheckList.REPOSITORY_KEY, SONAR_WAY_PROFILE_PATH);
        profile.done();
    }

}
