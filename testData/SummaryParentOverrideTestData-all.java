package data;

public class SummaryParentOverrideTestData {

    class GrandparentClass <fold text='{...}' expand='true'>{
        public void grandparentMethod()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Grandparent Method")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public void sharedMethod()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Shared method from Grandparent")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    class ParentClass extends GrandparentClass <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold>public void grandparentMethod() <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("Overridden Grandparent Method in Parent");
            <fold text='' expand='false'>System.out.</fold>println("Overridden Grandparent Method in Parent");
        }</fold>

        public void parentMethod()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Parent Method")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    interface FirstInterface <fold text='{...}' expand='true'>{
        void interfaceMethodOne();
        void sharedMethod();
    }</fold>

    interface SecondInterface <fold text='{...}' expand='true'>{
        void interfaceMethodTwo();
    }</fold>

    interface WithoutMethodInterface <fold text='{...}' expand='true'>{

    }</fold>

    public class TestDataClass extends ParentClass implements FirstInterface, SecondInterface, WithoutMethodInterface <fold text='{...}' expand='true'>{

        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold>public void interfaceMethodOne()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Implementation of Interface Method One")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold>public void interfaceMethodTwo()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Implementation of Interface Method Two")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold>public void sharedMethod()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Overridden Shared Method")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold>public void grandparentMethod()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Overridden Grandparent Method in TestDataClass")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public void additionalMethodOne()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Additional method one")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public void additionalMethodTwo()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Additional method two")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public void additionalMethodThree()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='false'>System.out.</fold>println("Additional method three")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    interface Repository<T> <fold text='{...}' expand='true'>{
        int size();
    }</fold>

    class NameRepository implements Repository<String> <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>int</fold><fold text='' expand='true'> </fold>size<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>0<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}