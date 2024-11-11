package backend.academy.command_line.converters;

import backend.academy.model.Format;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class FormatConverter implements IStringConverter<Format> {
    private final String optionName;

    public FormatConverter(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public Format convert(String value) {
        try {
            return Format.getFormat(value);
        } catch (IllegalArgumentException e) {
            throw new ParameterException("Invalid value for " + optionName + ": " + value, e);
        }
    }
}
