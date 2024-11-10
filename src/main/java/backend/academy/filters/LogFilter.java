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
        boolean withinDateRange = isWithinDateRange(record);
        if (!withinDateRange) {
            return false;
        }

        // Check filters
        for (Map.Entry<LogFilterField, Object> entry : config.filters().entrySet()) {
            LogFilterField field = entry.getKey();
            boolean logRecordFlag = switch (field) {
                case IP -> checkIp(record);
                case USER -> checkUser(record);
                case DATA -> checkData(record);
                case REQUEST_METHOD -> checkRequestMethod(record);
                case REQUEST_RESOURCE -> checkRequestResource(record);
                case REQUEST_PROTOCOL_VERSION -> checkRequestProtocolVersion(record);
                case RESPONSE_STATUS -> checkResponseStatus(record);
                case BODY_BYTES_SENT -> checkBodyBytesSent(record);
                case REFERER -> checkReferer(record);
                case USER_AGENT -> checkUserAgent(record);
            };

            if (!logRecordFlag) {
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

    private boolean checkIp(LogRecord record) {
        Object ip = config.filters().get(LogFilterField.IP);
        return record.ip().equals(ip);
    }

    private boolean checkUser(LogRecord record) {
        Object user = config.filters().get(LogFilterField.USER);
        return record.user().equals(user);
    }

    private boolean checkData(LogRecord record) {
        LocalDateTime dataFilter = (LocalDateTime) config.filters().get(LogFilterField.DATA);
        return record.timeLocal().isEqual(dataFilter);
    }

    private boolean checkRequestMethod(LogRecord record) {
        Object requestMethod = config.filters().get(LogFilterField.REQUEST_METHOD);
        return record.requestMethod().equals(requestMethod);
    }

    private boolean checkRequestResource(LogRecord record) {
        Object requestUrl = config.filters().get(LogFilterField.REQUEST_RESOURCE);
        return record.requestResource().equals(requestUrl);
    }

    private boolean checkRequestProtocolVersion(LogRecord record) {
        Object requestProtocolVersion = config.filters().get(LogFilterField.REQUEST_PROTOCOL_VERSION);
        return record.protocolVersion().equals(requestProtocolVersion);
    }

    private boolean checkResponseStatus(LogRecord record) {
        int responseStatus = (int) config.filters().get(LogFilterField.RESPONSE_STATUS);
        return record.responseCode().value() == responseStatus;
    }

    private boolean checkBodyBytesSent(LogRecord record) {
        int bodyBytesSent = (int) config.filters().get(LogFilterField.BODY_BYTES_SENT);
        return record.bodyBytesSize() == bodyBytesSent;
    }

    private boolean checkReferer(LogRecord record) {
        Object referer = config.filters().get(LogFilterField.REFERER);
        return record.referer().equals(referer);
    }

    private boolean checkUserAgent(LogRecord record) {
        Object userAgent = config.filters().get(LogFilterField.USER_AGENT);
        return record.userAgent().equals(userAgent);
    }

}
