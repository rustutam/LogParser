package backend.academy.filters;

import backend.academy.config.AppConfig;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class LogFilter {
    private final AppConfig config;

    public LogFilter(AppConfig config) {
        this.config = config;
    }

    public boolean filter(LogRecord record) {
        if (record == null) {
            return false;
        }

        // Check date range
        if (!isWithinDateRange(record)) {
            return false;
        }

        // Check filters
        for (Map.Entry<LogFilterField, Object> entry : config.filters().entrySet()) {
            if (!checkField(record, entry.getKey(), entry.getValue())) {
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

    private boolean checkField(LogRecord record, LogFilterField field, Object value) {
        return switch (field) {
            case IP -> record.ip().equals(value);
            case USER -> record.user().equals(value);
            case DATA -> record.timeLocal().isEqual((LocalDateTime) value);
            case REQUEST_METHOD -> record.request().requestMethod().equals(value);
            case REQUEST_RESOURCE -> record.request().requestResource().equals(value);
            case REQUEST_PROTOCOL_VERSION -> record.request().protocolVersion().equals(value);
            case RESPONSE_STATUS -> record.responseCode().value() == (int) value;
            case BODY_BYTES_SENT -> record.bodyBytesSize() == (int) value;
            case REFERER -> record.referer().equals(value);
            case USER_AGENT -> record.userAgent().contains((String) value);
        };
    }
}
