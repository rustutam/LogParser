package backend.academy.parser;

import backend.academy.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogParserTest {

    @Test
    void parseValidLogLine() {
        //Arrange
        String logLine =
            "127.0.0.1 - user [10/Oct/2020:13:55:36 +0000] \"GET /index.html HTTP/1.1\" 200 1234 \"http://example.com\" \"Mozilla/5.0\"";

        //Act
        Optional<LogRecord> result = LogParser.parseLogLine(logLine);

        //Assert
        assertTrue(result.isPresent());
        LogRecord record = result.get();

        assertEquals("127.0.0.1", record.ip());
        assertEquals("user", record.user());
        assertEquals(LocalDateTime.of(2020, 10, 10, 13, 55, 36).atOffset(ZoneOffset.UTC).toLocalDateTime(),
            record.timeLocal());
        assertEquals(new RequestModel("GET", "/index.html", "HTTP/1.1"), record.request());
        assertEquals(HttpStatusCode.OK, record.responseCode());
        assertEquals(1234, record.bodyBytesSize());
        assertEquals("http://example.com", record.referer());
        assertEquals("Mozilla/5.0", record.userAgent());
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

        assertTrue(result.isPresent());
        LogRecord record = result.get();

        assertEquals("127.0.0.1", record.ip());
        assertEquals("user", record.user());
        assertEquals(LocalDateTime.of(2020, 10, 10, 13, 55, 36).atOffset(ZoneOffset.UTC).toLocalDateTime(),
            record.timeLocal());
        assertEquals(new RequestModel("GET", "/index.html", "HTTP/1.1"), record.request());
        assertEquals(HttpStatusCode.OK, record.responseCode());
        assertEquals(1234, record.bodyBytesSize());
        assertEquals("-", record.referer());
        assertEquals("-", record.userAgent());
    }

}
