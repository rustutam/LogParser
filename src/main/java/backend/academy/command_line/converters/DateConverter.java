package backend.academy.command_line.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import lombok.extern.log4j.Log4j2;
import static backend.academy.config.Settings.INPUT_DATA_FORMATTER;

@Log4j2
public class DateConverter implements IStringConverter<LocalDateTime> {
    private final String optionName;

    public DateConverter(String optionName) {
        this.optionName = optionName;
    }

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
