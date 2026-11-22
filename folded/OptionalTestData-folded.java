package data;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class OptionalTestData {

    public void enter(Optional<Data> opt, Data data, Data dataNull) {
        Object o = null;
        if (opt.isPresent()) {
            o = opt!!;
        }
        o = opt!!;

        o = dataNull;
        o = data!!;

        o = dataNull ?: this::orElseGetReturn;
        o = dataNull ?: () -> data.getData().getData();

        o = data!!.data ?: null;
        o = dataNull.map(OptionalTestData::getOutsideData)!!;

        o = opt.map(Data::new).filter(Data.class::isInstance).map(Data.class::cast);

        o = data!!.map(Data::data)
                .map(Data::data)
                .filter(it -> it.ok)
                .map(Function.identity())?.ok
                .map(it -> !it).map(it -> {
                var s = it.toString();
        return s.equals("false");
                }) ?: null;

        o = data.getData()!!.map(OptionalTestData::getOutsideData)?.string ?: data.getString();

        data!!.flatMap(this::ofNullable).map(data::getDataMethod) ?: () -> getOutsideData(data);

        o = listOfNotNull(Optional.<Data>empty()?.data)*.data().filterNotNull().findAny()?.string!!;

        o = opt?.data ?: null;

        o = listOfNotNull(Optional.<Data>empty()?.data)*.data().filterNotNull().findAny()?.string!!;

        Stream.of(data)*.data().filterNotNull();
        Stream.of(data)*.data().filterNotNull()*.data().findFirst()!!;

        o = listOfNotNull(opt?.data);
        o = listOfNotNull(opt);
        o = listOfNotNull(dataNull?.data);
        o = Stream.of(opt).flatMap(it -> listOfNotNull(it?.data));
        o = opt.map(Data::data).map(Data::string).isPresent();
        o = opt.map(Data::data).isEmpty();
        opt.map(Data::data).ifPresent(System.out::println);
        o = opt.map(Data::data).filter(Data::isOk);
    }

    private Data orElseGetReturn() {
        return null;
    }

    private Optional<Data> ofNullable(Data data) {
        return Optional.empty();
    }

    private static Data getOutsideData(Data data) {
        return null;
    }

    static class Data {
        Data data;
        boolean ok;

        String string;

        public Data(Data data) {
            this.data = data;
        }

        public Data getData() {
                return data;
        }

        public boolean isOk() {
                return ok;
        }

        public void setData(Data data) {
                this.data = data;
        }

        public void setOk(boolean ok) {
                this.ok = ok;
        }

        public String getString() {
                return string;
        }

        public void setString(String string) {
                this.string = string;
        }

        public Data getDataMethod(Data data) {
                return data;
        }
    }
}
