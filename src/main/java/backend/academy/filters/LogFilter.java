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
        // Check date range
        if (!isWithinDateRange(record)) {
            return false;
        }

        // Check filters
        for (FilterField<?> filterField : config.filterFieldList()) {
            if (!checkField(record, filterField)) {
                return false;
            }
        }

            return true;
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
            case IpFilterField ignored -> record.ip().equals(filterField.value);
            case UserFilterField ignored -> record.user().equals(filterField.value);
            case DataFilterField ignored -> record.timeLocal().isEqual((LocalDateTime) filterField.value);
            case MethodFilterField ignored -> record.request().requestMethod().equals(filterField.value);
            case ResourceFilterField ignored -> record.request().requestResource().equals(filterField.value);
            case ProtocolFilterField ignored -> record.request().protocolVersion().equals(filterField.value);
            case ResponseCodeFilterField ignored -> record.responseCode().value() == ((int) filterField.value);
            case BytesFilterField ignored -> record.bodyBytesSize() == (int) filterField.value;
            case RefererFilterField ignored -> record.referer().equals(filterField.value);
            case AgentFilterField ignored -> record.userAgent().contains((String) filterField.value);
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };
    }

}

