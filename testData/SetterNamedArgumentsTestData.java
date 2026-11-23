package data;

public class SetterNamedArgumentsTestData {

    public void folded(Person source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> alice = <fold text='' expand='false'>new </fold>Person<fold text='(' expand='false'>();</fold>
        <fold text='n' expand='false'>alice.setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>alice.setA</fold>ge<fold text=' = ' expand='false'>(</fold>31<fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>alice.setA</fold>ctive<fold text=' = ' expand='false'>(</fold>true<fold text='' expand='false'>);</fold>

        <fold text='val' expand='false'>Person</fold> bob = <fold text='' expand='false'>new </fold>Person<fold text='(n' expand='false'>();
        bob.setN</fold>ame<fold text=' = ' expand='false'>(</fold>"Bob");

        <fold text='val' expand='false'>var</fold> copy = <fold text='' expand='false'>new </fold>Person<fold text='(' expand='false'>();</fold>
        <fold text='n' expand='false'>copy.setN</fold>ame<fold text=' = ' expand='false'>(</fold>source.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>copy.setA</fold>ge<fold text=' = ' expand='false'>(</fold>source.<fold text='a' expand='false'>getA</fold>ge<fold text='' expand='false'>()</fold> + 1<fold text='' expand='false'>);</fold>
        System.out.println(copy);

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
        System.out.println(empty);
    }</fold>

    static class Person <fold text='{...}' expand='true'>{
        private String name;
        private int age;
        private boolean active;

        Person() <fold text='{}' expand='true'>{
        }</fold>

        Person(String name)<fold text=' { ' expand='false'> {
            </fold>this.name = name;<fold text=' }' expand='false'>
        }</fold>

        public String getName()<fold text=' { ' expand='false'> {
            </fold>return name;<fold text=' }' expand='false'>
        }</fold>

        public void setName(String name)<fold text=' { ' expand='false'> {
            </fold>this.name = name;<fold text=' }' expand='false'>
        }</fold>

        public int getAge()<fold text=' { ' expand='false'> {
            </fold>return age;<fold text=' }' expand='false'>
        }</fold>

        public void setAge(int age)<fold text=' { ' expand='false'> {
            </fold>this.age = age;<fold text=' }' expand='false'>
        }</fold>

        public boolean isActive()<fold text=' { ' expand='false'> {
            </fold>return active;<fold text=' }' expand='false'>
        }</fold>

        public void setActive(boolean active)<fold text=' { ' expand='false'> {
            </fold>this.active = active;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
