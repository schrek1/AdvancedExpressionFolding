package data;

import java.util.List;

<fold text='@NoArgsConstructor @Getter p' expand='false'>p</fold>ublic class FieldShiftSetters {
    <fold text='@Setter @Setter p' expand='false'>p</fold>rivate String username;
    <fold text='@Setter @With p' expand='false'>p</fold>rivate boolean active;
    <fold text='@Setter @With p' expand='false'>p</fold>rivate String userIdentifier;
    <fold text='@Setter p' expand='false'>p</fold>rivate FieldShiftSetters child;
    <fold text='@Setter p' expand='false'>p</fold>rivate List<String> list;<fold text='' expand='false'>

    </fold><fold text='' expand='false'>public FieldShiftSetters() <fold text='{}' expand='true'>{
    }</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public void setUsername(String username)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>this.username = <fold text='<<' expand='false'>username</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public void setActive(boolean active)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>this.active = <fold text='<<' expand='false'>active</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public void setUserIdentifier(String userIdentifier)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>this.userIdentifier = <fold text='<<' expand='false'>userIdentifier</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public void setChild(FieldShiftSetters child)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>this.child = <fold text='<<' expand='false'>child</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public List<String> getList()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>list<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public void setList(List<String> list)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>this.list = <fold text='<<' expand='false'>list</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public void withUsername(String username)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>this.username = <fold text='<<' expand='false'>username</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public FieldShiftSetters withActive(boolean active) <fold text='{...}' expand='true'>{
        this.active = <fold text='<<' expand='false'>active</fold>;
        return this;
    }</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public FieldShiftSetters withUserIdentifier(String userIdentifier) <fold text='{...}' expand='true'>{
        this.userIdentifier = <fold text='<<' expand='false'>userIdentifier</fold>;
        return this;
    }</fold></fold>

    public static FieldShiftSetters mapPojoChain(FieldShiftSetters source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>FieldShiftSetters</fold> result = <fold text='' expand='false'>new </fold>FieldShiftSetters<fold text='(' expand='false'>();</fold>
        <fold text='u' expand='false'>result.setU</fold>sername<fold text=' = ' expand='false'>(</fold>source.<fold text='child' expand='false'>getChild()</fold><fold text='<<' expand='false'>.getUsername()</fold><fold text=',' expand='false'>);</fold>
        <fold text='u' expand='false'>result.setU</fold>serIdentifier<fold text=' = ' expand='false'>(</fold>source.<fold text='child' expand='false'>getChild()</fold>.<fold text='child' expand='false'>getChild()</fold>.<fold text='child' expand='false'>getChild()</fold><fold text='<<' expand='false'>.getUserIdentifier()</fold><fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>result.setA</fold>ctive<fold text=' = ' expand='false'>(</fold>source.<fold text='child' expand='false'>getChild()</fold><fold text='<<' expand='false'>.isActive()</fold>);
        return result;
    }</fold>

    public static FieldShiftSetters mapPojo(FieldShiftSetters source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>FieldShiftSetters</fold> result = <fold text='' expand='false'>new </fold>FieldShiftSetters<fold text='(' expand='false'>();</fold>
        <fold text='u' expand='false'>result.setU</fold>sername<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getUsername()</fold><fold text=',' expand='false'>);</fold>
        <fold text='u' expand='false'>result.setU</fold>serIdentifier<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getUserIdentifier()</fold><fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>result.setA</fold>ctive<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.isActive()</fold><fold text=',' expand='false'>);</fold>
        <fold text='l' expand='false'>result.setL</fold>ist<fold text=' = ' expand='false'>(</fold>List.copyOf(source.<fold text='list' expand='false'>getList()</fold>));
        result.withUsername(source<fold text='<<' expand='false'>.getUsername()</fold>);
        result.withActive(source<fold text='<<' expand='false'>.isActive()</fold>);
        result.withUserIdentifier(source<fold text='<<' expand='false'>.getUserIdentifier()</fold>);
        return result;
    }</fold>

    public static FieldShiftSetters mapRecord(UserDataRecord source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>FieldShiftSetters</fold> result = <fold text='' expand='false'>new </fold>FieldShiftSetters<fold text='(' expand='false'>();</fold>
        <fold text='u' expand='false'>result.setU</fold>sername<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.username()</fold><fold text=',' expand='false'>);</fold>
        <fold text='a' expand='false'>result.setA</fold>ctive<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.active()</fold><fold text=',' expand='false'>);</fold>
        <fold text='u' expand='false'>result.setU</fold>serIdentifier<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.userIdentifier()</fold>);
        result.withUsername(source<fold text='<<' expand='false'>.username()</fold>);
        result.withActive(source<fold text='<<' expand='false'>.active()</fold>);
        result.withUserIdentifier(source<fold text='<<' expand='false'>.userIdentifier()</fold>);
        return result;
    }</fold>

    public static FieldShiftSetters map(UserData2 source, FieldShiftSetters setters, UserDataRecord record) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>FieldShiftSetters</fold> var1 = setters;
        var1.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>record<fold text='<<' expand='false'>.username()</fold><fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>FieldShiftSetters</fold> var2 = var1;
        var2.<fold text='a' expand='false'>setA</fold>ctive<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.isActive()</fold><fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>FieldShiftSetters</fold> result = <fold text='' expand='false'>new </fold>FieldShiftSetters<fold text='(u' expand='false'>();
        result.setU</fold>sername<fold text=' = ' expand='false'>(</fold>record.<fold text='userIdentifier' expand='false'>userIdentifier()</fold>);
        result.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>changer(record.<fold text='username' expand='false'>username()</fold>)<fold text='' expand='false'>)</fold>;
        result.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getUsername()</fold><fold text='' expand='false'>)</fold>;
        result.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>var1.<fold text='child' expand='false'>getChild()</fold><fold text='<<' expand='false'>.getUsername()</fold><fold text='' expand='false'>)</fold>;
        result.<fold text='username = ' expand='false'>setUsername</fold><fold text='"${' expand='false'>(</fold>source.<fold text='username' expand='false'>getUsername()</fold><fold text='}' expand='false'> + "</fold>1"<fold text='' expand='false'>)</fold>;
        result.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>source.<fold text='username' expand='false'>getUsername()</fold> + source.<fold text='userIdentifier' expand='false'>getUserIdentifier()</fold><fold text='' expand='false'>)</fold>;
        result.<fold text='a' expand='false'>setA</fold>ctive<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.isActive()</fold><fold text='' expand='false'>)</fold>;
        result.<fold text='u' expand='false'>setU</fold>serIdentifier<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getUserIdentifier()</fold><fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>FieldShiftSetters</fold> setters2 = <fold text='' expand='false'>new </fold>FieldShiftSetters<fold text='(c' expand='false'>();
        setters2.setC</fold>hild<fold text=' = ' expand='false'>(</fold>var1<fold text='<<' expand='false'>.getChild()</fold>);
        var1.<fold text='u' expand='false'>setU</fold>serIdentifier<fold text=' = ' expand='false'>(</fold>source.<fold text='username' expand='false'>getUsername()</fold><fold text='' expand='false'>)</fold>;
        var1.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getUsername()</fold><fold text='' expand='false'>)</fold>;
        setters2.<fold text='child' expand='false'>getChild()</fold>;
        setters2.<fold text='a' expand='false'>setA</fold>ctive<fold text=' = ' expand='false'>(</fold>setters<fold text='<<' expand='false'>.isActive()</fold><fold text='' expand='false'>)</fold>;
        setters2.<fold text='u' expand='false'>setU</fold>sername<fold text=' = ' expand='false'>(</fold>setters<fold text='<<' expand='false'>.getUsername()</fold><fold text='' expand='false'>)</fold>;
        setters2.<fold text='u' expand='false'>setU</fold>serIdentifier<fold text=' = ' expand='false'>(</fold>record<fold text='<<' expand='false'>.userIdentifier()</fold><fold text='' expand='false'>)</fold>;
        result.<fold text='c' expand='false'>setC</fold>hild<fold text=' = ' expand='false'>(</fold>setters2<fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>FieldShiftSetters</fold> childBuilder2 = <fold text='' expand='false'>new </fold>FieldShiftSetters<fold text='(u' expand='false'>();
        childBuilder2.setU</fold>sername<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getUsername()</fold>);
        result.<fold text='c' expand='false'>setC</fold>hild<fold text=' = ' expand='false'>(</fold>childBuilder2.<fold text='child' expand='false'>getChild()</fold>.<fold text='child' expand='false'>getChild()</fold>.<fold text='child' expand='false'>getChild()</fold><fold text='<<' expand='false'>.getChild()</fold><fold text='' expand='false'>)</fold>;
        return result;
    }</fold>

    private static String changer(String username)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>username<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold><fold text='' expand='false'>



    </fold><fold text='' expand='false'>public String getUsername()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.username<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public boolean isActive()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.active<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public String getUserIdentifier()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.userIdentifier<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public FieldShiftSetters getChild()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.child<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold>

    <fold text='@NoArgsConstructor @Getter @Setter p' expand='false'>p</fold>ublic static class UserData2 <fold text='{...}' expand='true'>{
        private String username;
        private boolean active;
        private String userIdentifier;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public UserData2() <fold text='{}' expand='true'>{
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setUsername(String username)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.username = <fold text='<<' expand='false'>username</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setActive(boolean active)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.active = <fold text='<<' expand='false'>active</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setUserIdentifier(String userIdentifier)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.userIdentifier = <fold text='<<' expand='false'>userIdentifier</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getUsername()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.username<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isActive()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.active<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getUserIdentifier()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.userIdentifier<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    public record UserDataRecord(String username, boolean active, String userIdentifier) <fold text='{...}' expand='true'>{
    }</fold>
}
