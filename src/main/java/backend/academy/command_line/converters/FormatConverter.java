package backend.academy.command_line.converters;

import backend.academy.model.Format;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Класс-конвертер для преобразования строкового представления формата в объект {@link Format}.
 * Этот класс реализует интерфейс {@link IStringConverter} из JCommander.
 * <p>
 */
@Log4j2
@AllArgsConstructor
public class FormatConverter implements IStringConverter<Format> {
    private final String optionName;

    @Override
    public Format convert(String value) {
        try {
            return Format.getFormat(value);
        } catch (IllegalArgumentException e) {
            throw new ParameterException("Неверное значение для " + optionName + ": " + value, e);
        }
    }
}
