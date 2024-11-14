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
            case IpFilterField ipFilterField -> logRecord.ip().equals(ipFilterField.value);
            case UserFilterField userFilterField -> logRecord.user().equals(userFilterField.value);
            case DataFilterField dataFilterField -> logRecord.timeLocal().isEqual(dataFilterField.value);
            case MethodFilterField methodFilterField ->
                logRecord.request().requestMethod().equals(methodFilterField.value);
            case ResourceFilterField resourceFilterField ->
                logRecord.request().requestResource().equals(resourceFilterField.value);
            case ProtocolFilterField protocolFilterField ->
                logRecord.request().protocolVersion().equals(protocolFilterField.value);
            case ResponseCodeFilterField responseCodeFilterField ->
                logRecord.responseCode().value() == responseCodeFilterField.value;
            case BytesFilterField bytesFilterField -> logRecord.bodyBytesSize() == bytesFilterField.value;
            case RefererFilterField refererFilterField -> logRecord.referer().equals(refererFilterField.value);
            case AgentFilterField agentFilterField -> logRecord.userAgent().contains(agentFilterField.value);
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };
    }

}

