package data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LombokSneakyThrowsTestData {

    private String json = "{}";

    void save(Path file) {
        try {
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String load(Path file) {
        try {
            String content = Files.readString(file);
            if (content.isBlank()) {
                return json;
            }
            return content;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void copy(Path from, Path to) {
        try {
            Files.copy(from, to);
        } catch (IOException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void notWholeBody(Path file) {
        System.out.println(file);
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void wrapsAnotherException(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new UncheckedIOException(new IOException(e));
        }
    }

    public void catchWithMessage(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete " + file, e);
        }
    }

    public void catchThatLogs(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void withFinally(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("done");
        }
    }

    public void withResources(Path file) {
        try (var lines = Files.lines(file)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void twoCatches(Path file) {
        try {
            Files.delete(file);
            Thread.sleep(10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
