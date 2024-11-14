package backend.academy.filters;

import backend.academy.exceptions.InvalidFilterValueException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public sealed class IntegerFilterField extends FilterField<Integer> permits
    ResponseCodeFilterField,
    BytesFilterField {

    @Override
    public void parseValue(String value) throws InvalidFilterValueException {
        try {
            this.value = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            log.error("Ошибка преобразования строки {} к числу", value);
            throw new InvalidFilterValueException(
                "Неверное значение для " + this.getClass().getSimpleName() + ": " + value, e);
        }
    }
}
