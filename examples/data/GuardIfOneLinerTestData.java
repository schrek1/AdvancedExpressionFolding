package data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GuardIfOneLinerTestData {

    String nullGuard(String name) {
        if (name == null) {
            return null;
        }
        return name.trim();
    }

    boolean literalGuards(String name, int size) {
        if (size == 0) {
            return false;
        }
        if (size > 10) {
            return true;
        }
        return name.isEmpty();
    }

    int numberGuard(List<String> items) {
        if (items.isEmpty()) {
            return -1;
        }
        return items.size();
    }

    String stringGuard(String name) {
        if (name.isBlank()) {
            return "unknown";
        }
        return name;
    }

    String variableGuard(String name, String fallback) {
        if (name == null) {
            return fallback;
        }
        return name;
    }

    List<String> emptyListGuard(List<String> items) {
        if (items == null) {
            return List.of();
        }
        return items;
    }

    Map<String, Integer> emptyMapGuard(Map<String, Integer> counts) {
        if (counts == null) {
            return Collections.emptyMap();
        }
        return counts;
    }

    Optional<String> optionalGuard(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return Optional.of(name);
    }

    void voidGuard(String name) {
        if (name == null) {
            return;
        }
        System.out.println(name);
    }

    String braceOnSameLine(String name) {
        if (name == null) {return null;}
        return name;
    }

    List<String> callIsNotSimple(List<String> items) {
        if (items.isEmpty()) {
            return defaultItems("shop", 3);
        }
        return items;
    }

    List<String> moreThanOneStatement(List<String> items) {
        if (items.isEmpty()) {
            System.out.println("empty");
            return List.of();
        }
        return items;
    }

    String withElse(String name) {
        if (name == null) {
            return null;
        } else {
            return name.trim();
        }
    }

    String elseIfChain(int code) {
        if (code == 0) {
            return "zero";
        } else if (code == 1) {
            return "one";
        }
        return "many";
    }

    String withComment(String name) {
        if (name == null) {
            // nothing to trim
            return null;
        }
        return name.trim();
    }

    String qualifiedValue(String name) {
        if (name == null) {
            return this.fallbackName;
        }
        return name;
    }

    void throwGuard(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        System.out.println(name);
    }

    String quickReturnShape(String key) {
        String value = lookup(key);
        if (value == null) {
            return null;
        }
        return value.trim();
    }

    private String fallbackName = "shop";

    private List<String> defaultItems(String prefix, int count) {
        return List.of(prefix + count);
    }

    private String lookup(String key) {
        return key;
    }
}
