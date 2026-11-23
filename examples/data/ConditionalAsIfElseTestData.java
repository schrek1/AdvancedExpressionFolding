package data;

import java.time.LocalDateTime;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseTestData {

    // A1 - checked value inside a formatted template, `== null` orientation
    String labelNullFirst(Device device) {
        return device.getPlatform() == null ? "device '%s'".formatted(device.getName()) : "device '%s' (%s)".formatted(device.getName(), device.getPlatform());
    }

    // A2 - checked value inside a formatted template, `!= null` orientation
    String labelNotNullFirst(Device device) {
        return device.getPlatform() != null ? "device '%s' (%s)".formatted(device.getName(), device.getPlatform()) : "device '%s'".formatted(device.getName());
    }

    // B1 - checked value inside a concatenation, `== null` orientation
    String changedSuffixNullFirst(LocalDateTime changedAt) {
        return changedAt == null ? "" : ", changed at " + changedAt;
    }

    // B2 - checked value inside a concatenation, `!= null` orientation
    String changedSuffixNotNullFirst(LocalDateTime changedAt) {
        return changedAt != null ? ", changed at " + changedAt : "";
    }

    // B3 - checked value as an argument of another call
    String changedText(LocalDateTime changedAt) {
        return changedAt != null ? format(changedAt) : "";
    }

    // C1 - the value itself
    String nameOrUnknown(String name) {
        return name != null ? name : "unknown";
    }

    // C2 - the value itself, inverted
    String nameOrUnknownInverted(String name) {
        return name == null ? "unknown" : name;
    }

    // C3 - a getter as the checked value
    String platformOrDefault(Device device) {
        return device.getPlatform() != null ? device.getPlatform() : "generic";
    }

    // D1 - a call on the value
    String deviceName(Device device) {
        return device != null ? device.getName() : "unknown";
    }

    // D2 - a call on the value, inverted
    String deviceNameInverted(Device device) {
        return device == null ? "unknown" : device.getName();
    }

    // D3 - a call chain on the value
    String ownerEmail(Device device) {
        return device != null ? device.getOwner().getEmail() : "";
    }

    // E1 - `: null` tail
    String deviceNameOrNull(Device device) {
        return device != null ? device.getName() : null;
    }

    // E2 - `: null` tail, inverted
    String deviceNameOrNullInverted(Device device) {
        return device == null ? null : device.getName();
    }

    // F1 - nested ternary, honest Elvis chain
    String firstKnown(String primary, String fallback) {
        return primary != null ? primary : fallback != null ? fallback : "none";
    }

    // F2 - nested ternary, checked values inside larger expressions
    String describe(Device device) {
        return device == null ? "none" : device.getPlatform() == null ? device.getName() : device.getName() + " on " + device.getPlatform();
    }

    // G1 - multi-line, checked value inside a formatted template
    String labelMultiLine(Device device) {
        return device.getPlatform() == null
                ? "device '%s'".formatted(device.getName())
                : "device '%s' (%s)".formatted(device.getName(), device.getPlatform());
    }

    // G2 - multi-line, honest Elvis
    String deviceNameMultiLine(Device device) {
        return device != null
                ? device.getName()
                : "unknown";
    }

    // H1 - short ternary without a string branch stays a ternary
    int pageSize(int requested) {
        return requested > 0 ? requested : 20;
    }

    // H2 - long ternary without a string branch
    long retryDelayMillis(int attempt, long baseDelayMillis, long maxDelayMillis) {
        return attempt > 5 ? Math.min(maxDelayMillis, baseDelayMillis * attempt * attempt) : baseDelayMillis * attempt;
    }

    // H3 - string branch in an assignment
    String status(boolean active, String name) {
        String label = active ? "active: " + name : "inactive";
        return label;
    }

    // H4 - string branch as a call argument stays a ternary
    void printStatus(boolean active, String name) {
        System.out.println(active ? "active: " + name : "inactive");
    }

    private static String format(LocalDateTime time) {
        return time.toString();
    }

    static class Device {
        private String name;
        private String platform;
        private Owner owner;

        public String getName() {
            return name;
        }

        public String getPlatform() {
            return platform;
        }

        public Owner getOwner() {
            return owner;
        }
    }

    static class Owner {
        private String email;

        public String getEmail() {
            return email;
        }
    }
}
