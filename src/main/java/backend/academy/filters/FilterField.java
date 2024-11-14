package backend.academy.filters;

import lombok.Getter;

@Getter
public sealed class FilterField<T> permits
    IntegerFilterField,
    StringFilterField,
    DataFilterField {
    protected T value;

    public void parseValue(String value) {

    }
}


