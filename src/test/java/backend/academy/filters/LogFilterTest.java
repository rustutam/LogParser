package backend.academy.filters;

import backend.academy.config.AppConfig;
import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogFilterTest {

    private AppConfig mockConfig;
    private LogFilter logFilter;

    @BeforeEach
    void setUp() {
        mockConfig = mock(AppConfig.class);
        logFilter = new LogFilter(mockConfig);
        LocalDateTime configStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime configEndDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        List<FilterField<?>> filterFieldList = List.of(
            new IpFilterField().value("127.167.0.4"),
            new UserFilterField().value("User"),
            new MethodFilterField().value("GET"),
            new ResourceFilterField().value("/index.html"),
            new ProtocolFilterField().value("HTTP/1.1"),
            new ResponseCodeFilterField().value(200),
            new BytesFilterField().value(1000),
            new RefererFilterField().value("http://referer.com"),
            new AgentFilterField().value("mozilla")

        );

        when(mockConfig.startDate()).thenReturn(configStartDate);
        when(mockConfig.endDate()).thenReturn(configEndDate);
        when(mockConfig.filterFieldList()).thenReturn(filterFieldList);

    }

    @Test
    void testFilterAllCorrect() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertTrue(result);
    }

    @Test
    void testBeforeDateFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2022, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testAfterDateFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2025, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIpFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "192.168.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testUserFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User3",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testMethodFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("PUT", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testResourceFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/github.com", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testProtocolFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTPS");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCodeFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.NOT_FOUND,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testBytesSizeFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            0,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testRefererFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "-",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testAgentFilter() {
        // Arrange
        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Chrome"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDateFilter() {
        // Arrange
        LocalDateTime configStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime configEndDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        List<FilterField<?>> filterFieldList = List.of(
            new DateFilterField().value(LocalDateTime.of(2024, 6, 15, 0, 0))
        );

        when(mockConfig.startDate()).thenReturn(configStartDate);
        when(mockConfig.endDate()).thenReturn(configEndDate);
        when(mockConfig.filterFieldList()).thenReturn(filterFieldList);

        LocalDateTime logDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request = new RequestModel("GET", "/index.html", "HTTP/1.1");

        LogRecord logRecord = new LogRecord(
            "127.167.0.4",
            "User",
            logDate,
            request,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertTrue(result);
    }
}

