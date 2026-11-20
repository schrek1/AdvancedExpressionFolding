package data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("ALL")
public class CatchRethrowTestData {

    String readOrder(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) rethrow
    }

    void saveOrder(Path file, String order) {
        try {
            Files.writeString(file, order);
        } catch (IOException e) rethrow as UncheckedIOException
    }

    void waitForStock(Object lock) {
        try {
            lock.wait();
        } catch (InterruptedException | IllegalMonitorStateException e) rethrow as IllegalStateException finally {
            System.out.println("done");
        }
    }

    void withMessage(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete " + file, e);
        }
    }

    void logsFirst(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            System.out.println("delete failed");
            throw new RuntimeException(e);
        }
    }

    void plainRethrow(Path file) throws IOException {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw e;
        }
    }

    void wrapsOtherCause(Path file, RuntimeException previous) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException(previous);
        }
    }

    void withComment(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            // the file may be locked by the scanner
            throw new RuntimeException(e);
        }
    }

    void swallows(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
        }
    }
}
