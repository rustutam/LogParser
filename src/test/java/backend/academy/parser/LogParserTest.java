package backend.academy.parser;

import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LogParserTest {

    @Test
    void parseValidLogLine() {
        //Arrange
        String logLine =
            "127.0.0.1 - user [10/Oct/2020:13:55:36 +0000] \"GET /index.html HTTP/1.1\" 200 1234 \"http://example.com\" \"Mozilla/5.0\"";

        //Act
        Optional<LogRecord> result = LogParser.parseLogLine(logLine);

        //Assert
        LogRecord logRecord = result.orElseThrow();

        assertEquals("127.0.0.1", logRecord.ip());
        assertEquals("user", logRecord.user());
        assertEquals(LocalDateTime.of(2020, 10, 10, 13, 55, 36).atOffset(ZoneOffset.UTC).toLocalDateTime(),
            logRecord.timeLocal());
        assertEquals(new RequestModel("GET", "/index.html", "HTTP/1.1"), logRecord.request());
        assertEquals(HttpStatusCode.OK, logRecord.responseCode());
        assertEquals(1234, logRecord.bodyBytesSize());
        assertEquals("http://example.com", logRecord.referer());
        assertEquals("Mozilla/5.0", logRecord.userAgent());
    }

    @Test
    void parseInvalidLogLine() {
        String logLine = "invalid log line";
        Optional<LogRecord> result = LogParser.parseLogLine(logLine);

        assertFalse(result.isPresent());
    }

    @Test
    void parseLogLineWithMissingFields() {
        String logLine =
            "127.0.0.1 - user [10/Oct/2020:13:55:36 +0000] \"GET /index.html HTTP/1.1\" 200 1234 \"-\" \"-\"";
        Optional<LogRecord> result = LogParser.parseLogLine(logLine);

        LogRecord logRecord = result.orElseThrow();

        assertEquals("127.0.0.1", logRecord.ip());
        assertEquals("user", logRecord.user());
        assertEquals(LocalDateTime.of(2020, 10, 10, 13, 55, 36).atOffset(ZoneOffset.UTC).toLocalDateTime(),
            logRecord.timeLocal());
        assertEquals(new RequestModel("GET", "/index.html", "HTTP/1.1"), logRecord.request());
        assertEquals(HttpStatusCode.OK, logRecord.responseCode());
        assertEquals(1234, logRecord.bodyBytesSize());
        assertEquals("-", logRecord.referer());
        assertEquals("-", logRecord.userAgent());
    }

}
