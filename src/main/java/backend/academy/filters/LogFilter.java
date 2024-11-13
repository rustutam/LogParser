package backend.academy.filters;

import backend.academy.config.AppConfig;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import java.util.Optional;

public class LogFilter {
    private final AppConfig config;

    public LogFilter(AppConfig config) {
        this.config = config;
    }

    public boolean filter(LogRecord record) {
        return isWithinDateRange(record) &&
            config.filterFieldList().stream().allMatch(filterField -> checkField(record, filterField));
    }

    private boolean isWithinDateRange(LogRecord record) {
        Optional<LocalDateTime> startDateOpt = config.startDate();
        Optional<LocalDateTime> endDateOpt = config.endDate();
        LocalDateTime logDate = record.timeLocal();

        if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
            LocalDateTime startDate = startDateOpt.get();
            LocalDateTime endDate = endDateOpt.get();
            return !logDate.isBefore(startDate) && !logDate.isAfter(endDate);
        } else if (startDateOpt.isPresent()) {
            LocalDateTime startDate = startDateOpt.get();
            return !logDate.isBefore(startDate);
        } else if (endDateOpt.isPresent()) {
            LocalDateTime endDate = endDateOpt.get();
            return !logDate.isAfter(endDate);
        }
        return true;
    }

    private boolean checkField(LogRecord record, FilterField<?> filterField) {
        return switch (filterField) {
            case IpFilterField ipFilterField -> record.ip().equals(ipFilterField.value);
            case UserFilterField userFilterField -> record.user().equals(userFilterField.value);
            case DataFilterField dataFilterField -> record.timeLocal().isEqual(dataFilterField.value);
            case MethodFilterField methodFilterField -> record.request().requestMethod().equals(methodFilterField.value);
            case ResourceFilterField resourceFilterField -> record.request().requestResource().equals(resourceFilterField.value);
            case ProtocolFilterField protocolFilterField -> record.request().protocolVersion().equals(protocolFilterField.value);
            case ResponseCodeFilterField responseCodeFilterField -> record.responseCode().value() == responseCodeFilterField.value;
            case BytesFilterField bytesFilterField -> record.bodyBytesSize() == bytesFilterField.value;
            case RefererFilterField refererFilterField -> record.referer().equals(refererFilterField.value);
            case AgentFilterField agentFilterField -> record.userAgent().contains(agentFilterField.value);
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };
    }

}

