package backend.academy.filters;

import backend.academy.exceptions.InvalidFilterValueException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import lombok.extern.log4j.Log4j2;
import static backend.academy.config.Settings.INPUT_DATA_FORMATTER;

@Log4j2
public final class DateFilterField extends FilterField<LocalDateTime> {
    @Override
    public void parseValue(String value) throws InvalidFilterValueException {
        try {
            LocalDate date = LocalDate.parse(value, INPUT_DATA_FORMATTER);
            super.value = date.atStartOfDay();
        } catch (DateTimeParseException e) {
            log.error("Ошибка преобразования строки {} к дате", value);
            throw new InvalidFilterValueException(
                "Неверное значение для " + this.getClass().getSimpleName() + ": " + value, e);
        }
    }

}
