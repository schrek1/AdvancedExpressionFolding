package data;

import <fold text='...' expand='false'>java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;</fold>

@SuppressWarnings("ALL")
public class CatchRethrowTestData {

    String readOrder(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            return Files.readString(file);
        }</fold> catch (IOException e)<fold text=' rethrow' expand='false'> <fold text='{...}' expand='true'>{
            throw new RuntimeException(e);
        }</fold></fold>
    }</fold>

    void saveOrder(Path file, String order) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.writeString(file, order);
        }</fold> catch (IOException e)<fold text='' expand='true'> </fold><fold text=' rethrow as ' expand='true'><fold text='{...}' expand='true'>{
            throw new </fold>UncheckedIOException<fold text='' expand='true'>(e);
        }</fold></fold>
    }</fold>

    void waitForStock(Object lock) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            lock.wait();
        }</fold> catch (InterruptedException | IllegalMonitorStateException e)<fold text='' expand='true'> </fold><fold text=' rethrow as ' expand='true'><fold text='{...}' expand='true'>{
            throw new </fold>IllegalStateException<fold text='' expand='true'>(e);
        }</fold></fold> finally <fold text='{...}' expand='true'>{
            System.out.println("done");
        }</fold>
    }</fold>

    void withMessage(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch (IOException e) <fold text='{...}' expand='true'>{
            throw new RuntimeException("Cannot delete " + file, e);
        }</fold>
    }</fold>

    void logsFirst(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch (IOException e) <fold text='{...}' expand='true'>{
            System.out.println("delete failed");
            throw new RuntimeException(e);
        }</fold>
    }</fold>

    void plainRethrow(Path file) throws IOException <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch (IOException e) <fold text='{...}' expand='true'>{
            throw e;
        }</fold>
    }</fold>

    void wrapsOtherCause(Path file, RuntimeException previous) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch (IOException e) <fold text='{...}' expand='true'>{
            throw new RuntimeException(previous);
        }</fold>
    }</fold>

    void withComment(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch (IOException e) <fold text='{...}' expand='true'>{
            // the file may be locked by the scanner
            throw new RuntimeException(e);
        }</fold>
    }</fold>

    void swallows(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch (IOException e) {
        }
    }</fold>
}
