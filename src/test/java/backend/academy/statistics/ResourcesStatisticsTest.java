package backend.academy.statistics;

import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class ResourcesStatisticsTest {
    @Test
    void updateStatistics() {
        // Arrange
        ResourcesStatistics methodStatistics = new ResourcesStatistics();

        LocalDateTime logDate1 = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request1 = new RequestModel("GET", "t-bank.com", "HTTP/1.1");

        LogRecord logRecord1 = new LogRecord(
            "127.167.0.4",
            "User",
            logDate1,
            request1,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        LocalDateTime logDate2 = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request2 = new RequestModel("GET", "t-bank.com", "HTTP/1.1");

        LogRecord logRecord2 = new LogRecord(
            "127.167.0.4",
            "User",
            logDate2,
            request2,
            HttpStatusCode.OK,
            1000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        LocalDateTime logDate3 = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request3 = new RequestModel("PUT", "other-bank.com", "HTTP/1.1");

        LogRecord logRecord3 = new LogRecord(
            "127.167.0.4",
            "User",
            logDate3,
            request3,
            HttpStatusCode.OK,
            2000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        methodStatistics.updateStatistics(logRecord1);
        methodStatistics.updateStatistics(logRecord2);
        methodStatistics.updateStatistics(logRecord3);
        HashMap<String, Integer> statistics = methodStatistics.statistics();

        // Assert
        assertEquals(2, statistics.get("t-bank.com"));
        assertEquals(1, statistics.get("other-bank.com"));

    }
}
