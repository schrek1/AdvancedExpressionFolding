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
        boolean anyAdmin = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(User::isAdmin);
        boolean allPaid = orders<fold text='' expand='false'>.stream()</fold>.<fold text='all' expand='false'>allMatch</fold>(o -> o.<fold text='paid' expand='false'>isPaid()</fold>);
        boolean noneEmpty = <fold text='' expand='false'>Arrays.stream(</fold>names<fold text='' expand='false'>)</fold>.<fold text='none' expand='false'>noneMatch</fold>(String::isEmpty);
        boolean anyShipped = orders<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(Order::wasShipped);

        boolean anyLongName = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> user.<fold text='name' expand='false'>getName()</fold>.length() > 10);
        boolean allNamed = users<fold text='' expand='false'>.stream()</fold>.<fold text='all' expand='false'>allMatch</fold>(it -> it.<fold text='name' expand='false'>getName()</fold> != null);
        boolean noneBlank = users<fold text='' expand='false'>.stream()</fold><fold text='*.' expand='false'>
                .map(</fold><fold text='name()' expand='false'>User::getName</fold><fold text='' expand='false'>)</fold>
                .<fold text='none' expand='false'>noneMatch</fold>(String::isBlank);
        boolean anyKnownAdmin = users<fold text='' expand='false'>.stream()</fold><fold text='.filterNotNull()' expand='false'>
                .filter(Objects::nonNull</fold><fold text='' expand='false'>)</fold>
                .<fold text='any' expand='false'>anyMatch</fold>(User::isAdmin);

        boolean anyActive = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(isActive);
        boolean anySelf = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(StreamPredicateTestData::isSelf);
        boolean anyBlock = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> <fold text='{...}' expand='true'>{
            return user.<fold text='admin' expand='false'>isAdmin()</fold>;
        }</fold>);
        boolean anyNested = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> orders<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(it -> it.<fold text='owner' expand='false'>getOwner()</fold> == user));
        boolean anyOwner = users<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(user -> orders<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold>(order -> order.<fold text='owner' expand='false'>getOwner()</fold> == user));

        boolean notACollection = stream.anyMatch(User::isAdmin);
        boolean streamOf = Stream.of("a", "b").anyMatch(String::isEmpty);
    }</fold>

    static boolean isSelf(User user)<fold text=' { ' expand='false'> {
        </fold>return false;<fold text=' }' expand='false'>
    }</fold>

    static class User <fold text='{...}' expand='true'>{
        boolean admin;
        String name;

        public boolean isAdmin()<fold text=' { ' expand='false'> {
            </fold>return admin;<fold text=' }' expand='false'>
        }</fold>

        public String getName()<fold text=' { ' expand='false'> {
            </fold>return name;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        boolean paid;
        User owner;

        public boolean isPaid()<fold text=' { ' expand='false'> {
            </fold>return paid;<fold text=' }' expand='false'>
        }</fold>

        public User getOwner()<fold text=' { ' expand='false'> {
            </fold>return owner;<fold text=' }' expand='false'>
        }</fold>

        public boolean wasShipped()<fold text=' { ' expand='false'> {
            </fold>return owner != null;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
