package backend.academy.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public sealed class FilterField<T> permits
    IntegerFilterField,
    StringFilterField,
    DateFilterField {
    protected T value;

    public void parseValue(String value) {

    }
}


