package backend.academy.command_line.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static backend.academy.config.Settings.INPUT_DATA_FORMATTER;

/**
 * Класс-конвертер для преобразования строкового представления даты в объект {@link LocalDateTime}.
 * Этот класс реализует интерфейс {@link IStringConverter} из JCommander.
 * <p>
 * Ожидаемый формат входной строки определяется {@link backend.academy.config.Settings#INPUT_DATA_FORMATTER}.
 * </p>
 * <p>
 */
@Log4j2
@AllArgsConstructor
public class DateConverter implements IStringConverter<LocalDateTime> {
    private final String optionName;

    @Override
    public LocalDateTime convert(String inputDate) {
        try {
            LocalDate date = LocalDate.parse(inputDate, INPUT_DATA_FORMATTER);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new ParameterException("Неверное значение для " + optionName + ": " + inputDate, e);
        }
    }
}
