package backend.academy.filters;

public abstract class StringFilterField extends FilterField<String>{
    @Override
    public void parseValue(String value) {
        super.value = value;
    }
}
