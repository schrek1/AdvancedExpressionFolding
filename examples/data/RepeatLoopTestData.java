package data;

import java.util.List;

public class RepeatLoopTestData {

    void unusedIndex(int retries) {
        for (int i = 0; i < retries; i++) {
            ping();
        }
    }

    void prefixIncrement(int retries) {
        for (int attempt = 0; attempt < retries; ++attempt) {
            ping();
        }
    }

    void longIndexAndSingleStatement(long count) {
        for (long i = 0L; i < count; i++)
            ping();
    }

    void boundFromCall(List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            ping();
        }
    }

    void readIndexKeepsRange(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(i);
        }
    }

    void modifiedIndexKeepsRange(int count) {
        for (int i = 0; i < count; i++) {
            i += 1;
            ping();
        }
    }

    void otherStartKeepsRange(int count) {
        for (int i = 1; i < count; i++) {
            ping();
        }
    }

    void inclusiveBoundKeepsRange(int count) {
        for (int i = 0; i <= count; i++) {
            ping();
        }
    }

    private void ping() {
    }
}
