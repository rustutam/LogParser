package backend.academy.filters;

import backend.academy.config.AppConfig;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

/**
 * Класс для фильтрации логов на основе заданных критериев.
 * <p>
 * Этот класс использует конфигурацию {@link AppConfig} для фильтрации записей логов.
 * </p>
 */
@AllArgsConstructor
public class LogFilter {
    private final AppConfig config;

    /**
     * Фильтрует запись лога на основе заданных критериев.
     *
     * @param logRecord запись лога для фильтрации
     * @return true, если запись лога соответствует всем критериям фильтрации, иначе false
     */
    public boolean filter(LogRecord logRecord) {
        return isWithinDateRange(logRecord)
            &&
            config
                .filterFieldList()
                .stream()
                .allMatch(filterField -> checkField(logRecord, filterField));
    }

    /**
     * Проверяет, находится ли запись лога в заданном диапазоне дат.
     *
     * @param logRecord запись лога для проверки
     * @return true, если запись лога находится в заданном диапазоне дат, иначе false
     */
    private boolean isWithinDateRange(LogRecord logRecord) {
        LocalDateTime startDate = config.startDate();
        LocalDateTime endDate = config.endDate();
        LocalDateTime logDate = logRecord.timeLocal();

        return logDate.isAfter(startDate) && logDate.isBefore(endDate);

    }

    /**
     * Проверяет, соответствует ли поле записи лога заданному критерию фильтрации.
     *
     * @param logRecord запись лога для проверки
     * @param filterField поле фильтра для проверки
     * @return true, если поле записи лога соответствует критерию фильтрации, иначе false
     */
    private boolean checkField(LogRecord logRecord, FilterField<?> filterField) {
        return switch (filterField) {
            case IpFilterField ipFilterField -> logRecord
                .ip()
                .equalsIgnoreCase(ipFilterField.value);
            case UserFilterField userFilterField -> logRecord
                .user()
                .equalsIgnoreCase(userFilterField.value);
            case DateFilterField dateFilterField -> logRecord
                .timeLocal()
                .toLocalDate()
                .isEqual(dateFilterField.value.toLocalDate());
            case MethodFilterField methodFilterField -> logRecord
                .request()
                .requestMethod()
                .equalsIgnoreCase(methodFilterField.value);
            case ResourceFilterField resourceFilterField -> logRecord
                .request()
                .requestResource()
                .equalsIgnoreCase(resourceFilterField.value);
            case ProtocolFilterField protocolFilterField -> logRecord
                .request()
                .protocolVersion()
                .equalsIgnoreCase(protocolFilterField.value);
            case ResponseCodeFilterField responseCodeFilterField -> logRecord
                .responseCode()
                .value() == responseCodeFilterField.value;
            case BytesFilterField bytesFilterField -> logRecord
                .bodyBytesSize() == bytesFilterField.value;
            case RefererFilterField refererFilterField -> logRecord
                .referer()
                .equalsIgnoreCase(refererFilterField.value);
            case AgentFilterField agentFilterField -> logRecord
                .userAgent()
                .toLowerCase()
                .contains(agentFilterField.value.toLowerCase());
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };
    }

}

