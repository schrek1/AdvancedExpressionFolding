package data;

import <fold text='...' expand='false'>lombok.Builder;
import lombok.experimental.SuperBuilder;</fold>

public class LombokSuperBuilderTestData {

    public void superBuilders(Profile profile) <fold text='{...}' expand='true'>{
        Profile reviewed = profile.<fold text='copy(' expand='false'>toBuilder()</fold>
            <fold text='' expand='false'>.</fold>level<fold text=' = ' expand='false'>(</fold>"HIGH"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Alice"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>    .build(</fold>)<fold text='' expand='false'>;</fold>
        Profile created = Profile<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Bob"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>.</fold>level<fold text=' = ' expand='false'>(</fold>"LOW"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>    .build(</fold>)<fold text='' expand='false'>;</fold>
        Profile derived = Profile<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Carol")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    @SuperBuilder(toBuilder = true)
    public static class Base <fold text='{...}' expand='true'>{
        private final String name;
    }</fold>

    @SuperBuilder(toBuilder = true)
    public static class Profile extends Base <fold text='{...}' expand='true'>{
        private final String level;
    }</fold>
}
