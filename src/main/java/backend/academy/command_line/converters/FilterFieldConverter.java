package backend.academy.command_line.converters;

import backend.academy.exceptions.InvalidFilterFieldException;
import backend.academy.exceptions.InvalidFilterValueException;
import backend.academy.filters.AgentFilterField;
import backend.academy.filters.BytesFilterField;
import backend.academy.filters.DateFilterField;
import backend.academy.filters.FilterField;
import backend.academy.filters.IpFilterField;
import backend.academy.filters.MethodFilterField;
import backend.academy.filters.ProtocolFilterField;
import backend.academy.filters.RefererFilterField;
import backend.academy.filters.ResourceFilterField;
import backend.academy.filters.ResponseCodeFilterField;
import backend.academy.filters.UserFilterField;
import backend.academy.model.LogFilterField;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Класс-конвертер для преобразования строкового представления поля фильтра в объект {@link FilterField}.
 * Этот класс реализует интерфейс {@link IStringConverter} из JCommander.
 * <p>
 * Ожидаемый формат входной строки: "field:value", где "field" - это имя поля фильтра,
 * а "value" - значение, которое нужно распарсить для этого поля.
 * </p>
 */
@Log4j2
@AllArgsConstructor
public class FilterFieldConverter implements IStringConverter<FilterField<?>> {
    private final String optionName;

    @Override
    public FilterField<?> convert(String value) {
        String[] parts = value.split(":", 2);

        if (parts.length != 2) {
            throw new ParameterException("Фильтр должен быть в формате field:value");
        }

        String maybeField = parts[0];
        String maybeValue = parts[1];

        LogFilterField field;
        try {
            field = LogFilterField.getField(maybeField);
            return getFilterField(field, maybeValue);

        } catch (InvalidFilterFieldException e) {
            log.error("Неверное значение для {}: {}", optionName, maybeField);
            throw new ParameterException("Неверное значение для " + optionName + ": " + maybeField, e);
        } catch (InvalidFilterValueException e) {
            log.error("Неверное значение поля фильтрации {}: {}", maybeField, maybeValue);
            throw new ParameterException("Неверное значение поля фильтрации " + maybeField + ": " + maybeValue, e);
        }
    }

    private FilterField<?> getFilterField(LogFilterField field, String value) {
        FilterField<?> filterField =
            switch (field) {
                case IP -> new IpFilterField();
                case USER -> new UserFilterField();
                case DATE -> new DateFilterField();
                case REQUEST_METHOD -> new MethodFilterField();
                case REQUEST_RESOURCE -> new ResourceFilterField();
                case REQUEST_PROTOCOL_VERSION -> new ProtocolFilterField();
                case RESPONSE_CODE -> new ResponseCodeFilterField();
                case BODY_BYTES_SENT -> new BytesFilterField();
                case REFERER -> new RefererFilterField();
                case USER_AGENT -> new AgentFilterField();
            };
        filterField.parseValue(value);
        return filterField;
    }
}
