package data;

public class SetterNamedArgumentsTestData {

    public void folded(Person source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> alice = <fold text='' expand='false'>new </fold>Person<fold text='(' expand='false'>();</fold>
        <fold text='n' expand='false'>alice.setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>alice.setA</fold>ge<fold text=' = ' expand='false'>(</fold>31<fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>alice.setA</fold>ctive<fold text=' = ' expand='false'>(</fold>true);

        <fold text='val' expand='false'>Person</fold> bob = <fold text='' expand='false'>new </fold>Person<fold text='(n' expand='false'>();
        bob.setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Bob");

        <fold text='val' expand='false'>var</fold> copy = <fold text='' expand='false'>new </fold>Person<fold text='(' expand='false'>();</fold>
        <fold text='n' expand='false'>copy.setN</fold>ame<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getName()</fold><fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>copy.setA</fold>ge<fold text=' = ' expand='false'>(</fold>source.<fold text='a' expand='false'>getA</fold>ge<fold text='' expand='false'>()</fold> + 1);
        <fold text='' expand='false'>System.out.</fold>println(copy);

        <fold text='val' expand='false'>var</fold> partial = <fold text='' expand='false'>new </fold>Person<fold text='(n' expand='false'>();
        partial.setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Carol");
        partial.<fold text='a' expand='false'>setA</fold>ge<fold text=' = ' expand='false'>(</fold>partial.<fold text='a' expand='false'>getA</fold>ge<fold text='' expand='false'>()</fold> + 1<fold text='' expand='false'>)</fold>;

        <fold text='val' expand='false'>var</fold> renamed = <fold text='' expand='false'>new </fold>Person<fold text='(n' expand='false'>();
        renamed.setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Heidi");
        renamed.<fold text='n' expand='false'>setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Ivan"<fold text='' expand='false'>)</fold>;
    }</fold>

    public void notFolded(Person source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> withArguments = new Person("Dave");
        withArguments.<fold text='a' expand='false'>setA</fold>ge<fold text=' = ' expand='false'>(</fold>40<fold text='' expand='false'>)</fold>;

        <fold text='val' expand='false'>var</fold> commented = new Person();
        // a comment keeps the setters as they are
        commented.<fold text='n' expand='false'>setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Eve"<fold text='' expand='false'>)</fold>;

        <fold text='val' expand='false'>var</fold> other = new Person();
        source.<fold text='n' expand='false'>setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Frank"<fold text='' expand='false'>)</fold>;

        <fold text='val' expand='false'>var</fold> anonymous = new Person() <fold text='{...}' expand='true'>{
        }</fold>;
        anonymous.<fold text='n' expand='false'>setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Grace"<fold text='' expand='false'>)</fold>;

        <fold text='val' expand='false'>var</fold> empty = new Person();
        <fold text='' expand='false'>System.out.</fold>println(empty);
    }</fold>

    <fold text='@NoArgsConstructor(default) @Getter @Setter s' expand='false'>s</fold>tatic class Person <fold text='{...}' expand='true'>{
        <fold text='@Constructor(default) p' expand='false'>p</fold>rivate String name;
        private int age;
        private boolean active;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Person() <fold text='{}' expand='true'>{
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>Person(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.name = <fold text='<<' expand='false'>name</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setName(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.name = <fold text='<<' expand='false'>name</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public int getAge()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>age<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setAge(int age)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.age = <fold text='<<' expand='false'>age</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isActive()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>active<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setActive(boolean active)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.active = <fold text='<<' expand='false'>active</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>
}
