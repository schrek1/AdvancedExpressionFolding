package data;

import <fold text='...' expand='false'>java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;</fold>

@SuppressWarnings("ALL")
public class CatchRethrowTestData {

    <fold text='@SneakyThrows S' expand='true'>S</fold>tring readOrder(Path file) <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold>return Files.readString(file);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw new RuntimeException(e);
        }</fold></fold>
    }</fold>

    <fold text='@SneakyThrows(UncheckedIOException) v' expand='true'>v</fold>oid saveOrder(Path file, String order) <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold>Files.writeString(file, order);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw new UncheckedIOException(e);
        }</fold></fold>
    }</fold>

    void waitForStock(Object lock) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            lock.wait();
        }</fold> catch <fold text='' expand='false'>(</fold>InterruptedException | IllegalMonitorStateException e<fold text='' expand='false'>)</fold><fold text='' expand='true'> </fold><fold text=' rethrow as ' expand='true'><fold text='{...}' expand='true'>{
            throw new </fold>IllegalStateException<fold text='' expand='true'>(e);
        }</fold></fold> finally <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("done");
        }</fold>
    }</fold>

    void withMessage(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw new RuntimeException("Cannot delete <fold text='$' expand='false'>" + </fold>file<fold text='",' expand='false'>,</fold> e);
        }</fold>
    }</fold>

    void logsFirst(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("delete failed");
            throw new RuntimeException(e);
        }</fold>
    }</fold>

    void plainRethrow(Path file) throws IOException <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw e;
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void wrapsOtherCause(Path file, RuntimeException previous) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw new RuntimeException(previous);
        }</fold>
    }</fold>

    <fold text='@SneakyThrows v' expand='true'>v</fold>oid withComment(Path file) <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold>Files.delete(file);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            // the file may be locked by the scanner
            throw new RuntimeException(e);
        }</fold></fold>
    }</fold>

    void swallows(Path file) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> {
        }<fold text=' ' expand='true'>
    </fold>}</fold>
}
