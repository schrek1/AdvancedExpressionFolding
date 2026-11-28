package data;

public class FormattedInExpressionFunctionTestData {
    static class Device {
        private String name;
        private String platform;

        String getName() { name }

        String getPlatform() { platform }
    }

    String describe(Device device) { "device ${device.name} (${device.platform})" }

    String describeName(Device device) { "device ${device.name}" }

    String describeInBlock(Device device) {
        String text = "device ${device.name}";
        return text;
    }

    String describeNullable(Device device) {
        return if (device.platform == null) "device ${device.name}" else "device ${device.name} (${device.platform})"
    }
}
