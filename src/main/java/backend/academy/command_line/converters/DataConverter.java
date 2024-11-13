package backend.academy.command_line.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import static backend.academy.config.Settings.INPUT_DATA_FORMATTER;

@Log4j2
public class DataConverter implements IStringConverter<LocalDateTime> {
    private final String optionName;

    public DataConverter(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public LocalDateTime convert(String data) {
        try {
            LocalDate date = LocalDate.parse(data, INPUT_DATA_FORMATTER);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new ParameterException("Неверное значение для " + optionName + ": " + data, e);
        }
    }
}
