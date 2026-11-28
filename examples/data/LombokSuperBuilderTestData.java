package data;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

public class LombokSuperBuilderTestData {

    public void superBuilders(Profile profile) {
        Profile reviewed = profile.toBuilder()
            .level("HIGH")
            .name("Alice")
            .build();
        Profile created = Profile.builder()
            .name("Bob")
            .level("LOW")
            .build();
        Profile derived = Profile.builder().name("Carol").build();
    }

    @SuperBuilder(toBuilder = true)
    public static class Base {
        private final String name;
    }

    @SuperBuilder(toBuilder = true)
    public static class Profile extends Base {
        private final String level;
    }
}
