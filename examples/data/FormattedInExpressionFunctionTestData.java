package data;

public class FormattedInExpressionFunctionTestData {
    static class Device {
        private String name;
        private String platform;

        String getName() {
            return name;
        }

        String getPlatform() {
            return platform;
        }
    }

    String describe(Device device) {
        return "device %s (%s)".formatted(device.getName(), device.getPlatform());
    }

    String describeName(Device device) {
        return "device %s".formatted(device.getName());
    }

    String describeInBlock(Device device) {
        String text = "device %s".formatted(device.getName());
        return text;
    }

    String describeNullable(Device device) {
        return device.getPlatform() == null ? "device %s".formatted(device.getName()) : "device %s (%s)".formatted(device.getName(), device.getPlatform());
    }
}
