package data;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

public class LombokSuperBuilderTestData {

    public void superBuilders(Profile profile) {
        Profile reviewed = profile.copy(
            level = "HIGH",
            name = "Alice"
        )
        Profile created = Profile(
            name = "Bob",
            level = "LOW"
        )
        Profile derived = Profile(name = "Carol");
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
