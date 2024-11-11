package backend.academy.command_line.converters;

import backend.academy.filters.LogFilterField;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class LogFilterFieldConverter implements IStringConverter<LogFilterField> {
    private final String optionName;

    public LogFilterFieldConverter(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public LogFilterField convert(String value) {
        try {
            return LogFilterField.getFiled(value);
        } catch (IllegalArgumentException e) {
            throw new ParameterException("Invalid value for " + optionName + ": " + value, e);
        }
    }
}
