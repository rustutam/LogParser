package backend.academy.filters;

public class IntegerFilterField extends FilterField<Integer> {
    @Override
    public void parseValue(String value) {
        this.value = Integer.parseInt(value);
    }
}
