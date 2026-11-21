package data;

import java.util.List;

public class RepeatLoopTestData {

    void unusedIndex(int retries) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'><fold text='val' expand='false'>int</fold> i = 0; i < </fold>retries<fold text='' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void prefixIncrement(int retries) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'><fold text='val' expand='false'>int</fold> attempt = 0; attempt < </fold>retries<fold text='' expand='false'>; ++attempt</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void longIndexAndSingleStatement(long count) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'><fold text='val' expand='false'>long</fold> i = 0L; i < </fold>count<fold text='' expand='false'>; i++</fold>)
            ping();<fold text=' ' expand='true'>
    </fold>}</fold>

    void boundFromCall(List<String> names) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='repeat' expand='false'>for </fold>(<fold text='' expand='false'><fold text='val' expand='false'>int</fold> i = 0; i < </fold>names.size()<fold text='' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            ping();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void readIndexKeepsRange(int count) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>int</fold> </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..<' expand='false'>; i < </fold>count<fold text='' expand='false'>; i++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(i);
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void modifiedIndexKeepsRange(int count) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='var' expand='false'>int</fold> </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..<' expand='false'>; i < </fold>count<fold text='' expand='false'>; i++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            i += 1;
            ping();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void otherStartKeepsRange(int count) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>int</fold> </fold>i<fold text=' in ' expand='false'> = </fold>1<fold text='..<' expand='false'>; i < </fold>count<fold text='' expand='false'>; i++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            ping();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void inclusiveBoundKeepsRange(int count) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>int</fold> </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..' expand='false'>; i <= </fold>count<fold text='' expand='false'>; i++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            ping();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    private void ping() <fold text='{}' expand='true'>{
    }</fold>
}
