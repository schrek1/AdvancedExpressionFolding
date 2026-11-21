package data;

import java.util.List;

public class RepeatLoopTestData {

    void unusedIndex(int retries) {
        repeat(retries) {
            ping();
        }
    }

    void prefixIncrement(int retries) {
        repeat(retries) {
            ping();
        }
    }

    void longIndexAndSingleStatement(long count) {
        repeat(count)
            ping();
    }

    void boundFromCall(List<String> names) {
        repeat(names.size()) {
            ping();
        }
    }

    void readIndexKeepsRange(int count) {
        for (int i : [0, count)) {
            System.out.println(i);
        }
    }

    void modifiedIndexKeepsRange(int count) {
        for (int i : [0, count)) {
            i += 1;
            ping();
        }
    }

    void otherStartKeepsRange(int count) {
        for (int i : [1, count)) {
            ping();
        }
    }

    void inclusiveBoundKeepsRange(int count) {
        for (int i : [0, count]) {
            ping();
        }
    }

    private void ping() {
    }
}
