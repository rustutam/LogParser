package backend.academy.command_line.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataConverter implements IStringConverter<LocalDateTime> {
    private final String optionName;

    public DataConverter(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public LocalDateTime convert(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(data, formatter);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new ParameterException("Invalid value for " + optionName + ": " + data, e);
        }
    }
}
