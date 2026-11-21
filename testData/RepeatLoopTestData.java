package data;

import java.util.List;

public class RepeatLoopTestData {

    void unusedIndex(int retries) <fold text='{...}' expand='true'>{
        <fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'>int i = 0; i < </fold>retries<fold text='' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold>
    }</fold>

    void prefixIncrement(int retries) <fold text='{...}' expand='true'>{
        <fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'>int attempt = 0; attempt < </fold>retries<fold text='' expand='false'>; ++attempt</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold>
    }</fold>

    void longIndexAndSingleStatement(long count) <fold text='{...}' expand='true'>{
        <fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'>long i = 0L; i < </fold>count<fold text='' expand='false'>; i++</fold>)
            ping();
    }</fold>

    void boundFromCall(List<String> names) <fold text='{...}' expand='true'>{
        <fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'>int i = 0; i < </fold>names.size()<fold text='' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold>
    }</fold>

    void readIndexKeepsRange(int count) <fold text='{...}' expand='true'>{
        for (int i<fold text=' : [' expand='false'> = </fold>0<fold text=', ' expand='false'>; i < </fold>count<fold text=')' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
    }</fold>

    void modifiedIndexKeepsRange(int count) <fold text='{...}' expand='true'>{
        for (int i<fold text=' : [' expand='false'> = </fold>0<fold text=', ' expand='false'>; i < </fold>count<fold text=')' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            i += 1;
            ping();
        }</fold>
    }</fold>

    void otherStartKeepsRange(int count) <fold text='{...}' expand='true'>{
        for (int i<fold text=' : [' expand='false'> = </fold>1<fold text=', ' expand='false'>; i < </fold>count<fold text=')' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold>
    }</fold>

    void inclusiveBoundKeepsRange(int count) <fold text='{...}' expand='true'>{
        for (int i<fold text=' : [' expand='false'> = </fold>0<fold text=', ' expand='false'>; i <= </fold>count<fold text=']' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold>
    }</fold>

    private void ping() <fold text='{}' expand='true'>{
    }</fold>
}
