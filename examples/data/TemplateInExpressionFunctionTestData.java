package data;

import java.time.LocalDateTime;

public class TemplateInExpressionFunctionTestData {
    String changedSuffix(LocalDateTime changedAt) {
        return changedAt == null ? "" : ", changed at " + changedAt;
    }

    String greeting(String name) {
        return "Hello, " + name + "!";
    }

    String label(String name) {
        return "Name: " + name;
    }

    String prefixed(String name) {
        return name + " is here";
    }

    String trimmed(String name) {
        return "Name: " + name.trim();
    }

    String trimmedOrEmpty(String name) {
        return name == null ? "none" : "Name: " + name.trim();
    }
}
