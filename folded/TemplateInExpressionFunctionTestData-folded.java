package data;

import java.time.LocalDateTime;

public class TemplateInExpressionFunctionTestData {
    String changedSuffix(LocalDateTime changedAt) { if (changedAt == null) "" else ", changed at $changedAt" }

    String greeting(String name) { "Hello, $name!" }

    String label(String name) { "Name: $name" }

    String prefixed(String name) { name is here" }

    String trimmed(String name) { "Name: ${name.trim()}" }

    String trimmedOrEmpty(String name) { if (name == null) "none" else "Name: ${name.trim()}" }
}
