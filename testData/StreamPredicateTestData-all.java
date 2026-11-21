package data;

import <fold text='...' expand='false'>java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;</fold>

@SuppressWarnings("ALL")
public class StreamPredicateTestData {
    public static void main(List<User> users, Set<Order> orders, String[] names, Stream<User> stream, Predicate<User> isActive) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>boolean</fold> anyAdmin = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(User::isAdmin);
        <fold text='val' expand='false'>boolean</fold> allPaid = orders<fold text='' expand='false'>.stream()</fold>.<fold text='all' expand='false'>allMatch</fold>(o -> o.<fold text='paid' expand='false'>isPaid()</fold>);
        <fold text='val' expand='false'>boolean</fold> noneEmpty = <fold text='' expand='false'>Arrays.stream(</fold>names<fold text='' expand='false'>)</fold>.<fold text='none' expand='false'>noneMatch</fold>(String::isEmpty);
        <fold text='val' expand='false'>boolean</fold> anyShipped = orders<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(Order::wasShipped);

        <fold text='val' expand='false'>boolean</fold> anyLongName = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> user.<fold text='name' expand='false'>getName()</fold>.length() > 10);
        <fold text='val' expand='false'>boolean</fold> allNamed = users<fold text='' expand='false'>.stream()</fold>.<fold text='all' expand='false'>allMatch</fold>(it -> it.<fold text='name' expand='false'>getName()</fold> != null);
        <fold text='val' expand='false'>boolean</fold> noneBlank = users<fold text='' expand='false'>.stream()</fold><fold text='*.' expand='false'>
                .map(</fold><fold text='name()' expand='false'>User::getName</fold><fold text='' expand='false'>)</fold>
                .<fold text='none' expand='false'>noneMatch</fold>(String::isBlank);
        <fold text='val' expand='false'>boolean</fold> anyKnownAdmin = users<fold text='' expand='false'>.stream()</fold><fold text='.filterNotNull()' expand='false'>
                .filter(Objects::nonNull</fold><fold text='' expand='false'>)</fold>
                .<fold text='any' expand='false'>anyMatch</fold>(User::isAdmin);

        <fold text='val' expand='false'>boolean</fold> anyActive = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(isActive);
        <fold text='val' expand='false'>boolean</fold> anySelf = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(StreamPredicateTestData::isSelf);
        <fold text='val' expand='false'>boolean</fold> anyBlock = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> <fold text='{...}' expand='true'>{
            return user.<fold text='admin' expand='false'>isAdmin()</fold>;
        }</fold>);
        <fold text='val' expand='false'>boolean</fold> anyNested = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> orders<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(it -> it.<fold text='owner' expand='false'>getOwner()</fold> == user));
        <fold text='val' expand='false'>boolean</fold> anyOwner = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> orders<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(order -> order.<fold text='owner' expand='false'>getOwner()</fold> == user));

        <fold text='val' expand='false'>boolean</fold> notACollection = stream.anyMatch(User::isAdmin);
        <fold text='val' expand='false'>boolean</fold> streamOf = Stream.of("a", "b").anyMatch(String::isEmpty);
    }</fold>

    static boolean isSelf(User user)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>false<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class User <fold text='{...}' expand='true'>{
        boolean admin;
        String name;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isAdmin()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>admin<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Order <fold text='{...}' expand='true'>{
        boolean paid;
        User owner;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isPaid()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>paid<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public User getOwner()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>owner<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        public boolean wasShipped()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>owner != null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
