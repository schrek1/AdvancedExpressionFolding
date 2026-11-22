package data;

import <fold text='...' expand='false'>java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;</fold>

public class LombokSneakyThrowsTestData {

    private String json = "{}";

    <fold text='@SneakyThrows v' expand='true'>v</fold>oid save(Path file) <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold>Files.writeString(file, json);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold>
    }</fold>

    <fold text='@SneakyThrows p' expand='true'>p</fold>ublic String load(Path file) <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold><fold text='val' expand='false'>String</fold> content = Files.readString(file);
        <fold text='' expand='true'>    </fold>if <fold text='' expand='false'>(</fold>content.<fold text='blank' expand='false'>isBlank()</fold><fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            <fold text='' expand='true'>    </fold></fold>return json;<fold text='' expand='false'>
        <fold text='' expand='true'>    </fold>}</fold></fold>
        <fold text='' expand='true'>    </fold>return content;<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>IllegalStateException(e);
        }</fold></fold>
    }</fold>

    <fold text='@SneakyThrows p' expand='true'>p</fold>ublic void copy(Path from, Path to) <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold>Files.copy(from, to);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException | SecurityException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold>
    }</fold>

    <fold text='@SneakyThrows(UncheckedIOException) p' expand='true'>p</fold>ublic void delete(Path file) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>try </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold><fold text='' expand='true'>
            </fold>Files.delete(file);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>UncheckedIOException(e);
        }</fold></fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void notWholeBody(Path file) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>System.out.</fold>println(file);
        <fold text='@SneakyThrows' expand='true'>try</fold><fold text='' expand='true'> </fold><fold text='' expand='true'><fold text='{...}' expand='true'>{</fold>
        <fold text='' expand='true'>    </fold>Files.delete(file);<fold text='' expand='true'>
        </fold><fold text='' expand='true'>}</fold></fold><fold text='' expand='true'> </fold><fold text='' expand='true'>catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold>
    }</fold>

    public void wrapsAnotherException(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>UncheckedIOException(<fold text='' expand='false'>new </fold>IOException(e));
        }</fold>
    }</fold>

    public void catchWithMessage(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException("Cannot delete <fold text='$' expand='false'>" + </fold>file<fold text='",' expand='false'>,</fold> e);
        }</fold>
    }</fold>

    public void catchThatLogs(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            System.err.println(e.<fold text='message' expand='false'>getMessage()</fold>);
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold>
    }</fold>

    public void withFinally(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold><fold text=' rethrow' expand='false'> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold> finally <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("done");
        }</fold>
    }</fold>

    public void withResources(Path file) <fold text='{...}' expand='true'>{
        try (var lines = Files.lines(file)) <fold text='{...}' expand='true'>{
            lines.forEach(System.out::println);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold><fold text=' rethrow' expand='false'> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold>
    }</fold>

    public void twoCatches(Path file) <fold text='{...}' expand='true'>{
        try <fold text='{...}' expand='true'>{
            Files.delete(file);
            Thread.sleep(10);
        }</fold> catch <fold text='' expand='false'>(</fold>IOException e<fold text='' expand='false'>)</fold><fold text=' rethrow' expand='false'> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold> catch <fold text='' expand='false'>(</fold>InterruptedException e<fold text='' expand='false'>)</fold><fold text=' rethrow' expand='false'> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>RuntimeException(e);
        }</fold></fold>
    }</fold>
}
