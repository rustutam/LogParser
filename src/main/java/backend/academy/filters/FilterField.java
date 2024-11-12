package backend.academy.filters;

import lombok.Getter;

@Getter
public abstract class FilterField<T>{
    protected T value;

    public void parseValue(String value) {

    }
}


