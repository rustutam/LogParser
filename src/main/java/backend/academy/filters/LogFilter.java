package backend.academy.filters;

import backend.academy.config.AppConfig;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;

public class LogFilter {
    private final AppConfig config;

    public LogFilter(AppConfig config) {
        this.config = config;
    }

    public boolean filter(LogRecord logRecord) {
        return isWithinDateRange(logRecord)
            && config.filterFieldList().stream().allMatch(filterField -> checkField(logRecord, filterField));
    }

    private boolean isWithinDateRange(LogRecord logRecord) {
        LocalDateTime startDate = config.startDate();
        LocalDateTime endDate = config.endDate();
        LocalDateTime logDate = logRecord.timeLocal();

        return logDate.isAfter(startDate) && logDate.isBefore(endDate);

    }

    private boolean checkField(LogRecord logRecord, FilterField<?> filterField) {
        return switch (filterField) {
            case IpFilterField ipFilterField -> logRecord.ip().equalsIgnoreCase(ipFilterField.value);
            case UserFilterField userFilterField -> logRecord.user().equalsIgnoreCase(userFilterField.value);
            case DateFilterField dateFilterField ->
                logRecord.timeLocal().toLocalDate().isEqual(dateFilterField.value.toLocalDate());
            case MethodFilterField methodFilterField ->
                logRecord.request().requestMethod().equalsIgnoreCase(methodFilterField.value);
            case ResourceFilterField resourceFilterField ->
                logRecord.request().requestResource().equalsIgnoreCase(resourceFilterField.value);
            case ProtocolFilterField protocolFilterField ->
                logRecord.request().protocolVersion().equalsIgnoreCase(protocolFilterField.value);
            case ResponseCodeFilterField responseCodeFilterField ->
                logRecord.responseCode().value() == responseCodeFilterField.value;
            case BytesFilterField bytesFilterField -> logRecord.bodyBytesSize() == bytesFilterField.value;
            case RefererFilterField refererFilterField ->
                logRecord.referer().equalsIgnoreCase(refererFilterField.value);
            case AgentFilterField agentFilterField ->
                logRecord.userAgent().toLowerCase().contains(agentFilterField.value.toLowerCase());
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };
    }

}

