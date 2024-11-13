package backend.academy.command_line.converters;

import backend.academy.model.Format;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
            throw new ParameterException("Неверное значение для " + optionName + ": " + value, e);
        }
    }
}
