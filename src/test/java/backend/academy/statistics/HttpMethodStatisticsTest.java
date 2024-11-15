package backend.academy.statistics;

import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpMethodStatisticsTest {

    @Test
    void updateStatistics() {
        // Arrange
        HttpMethodStatistics methodStatistics = new HttpMethodStatistics();

        LocalDateTime logDate1 = LocalDateTime.of(2024, 6, 15, 12, 0);
        RequestModel request1 = new RequestModel("GET", "/index.html", "HTTP/1.1");

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
        RequestModel request2 = new RequestModel("PUT", "/index.html", "HTTP/1.1");

        LogRecord logRecord2 = new LogRecord(
            "127.167.0.4",
            "User",
            logDate2,
            request2,
            HttpStatusCode.OK,
            2000,
            "http://referer.com",
            "Mozilla/5.0"
        );

        // Act
        methodStatistics.updateStatistics(logRecord1);
        methodStatistics.updateStatistics(logRecord2);
        HashMap<String, Integer> statistics = methodStatistics.statistics();

        // Assert
        assertEquals(1, statistics.get("GET"));
        assertEquals(1, statistics.get("PUT"));

    }
}
