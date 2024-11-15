package backend.academy.statistics;

import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.Report;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneralStatisticsTest {

    @Test
    void updateStatistics() {
        // Arrange
        String fileName = "file1";
        LocalDateTime startDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 6, 15, 12, 0);
        GeneralStatistics generalStatistics = new GeneralStatistics(
            List.of(fileName),
            startDate,
            endDate
        );

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
        RequestModel request2 = new RequestModel("GET", "/index.html", "HTTP/1.1");

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
        generalStatistics.updateStatistics(logRecord1);
        generalStatistics.updateStatistics(logRecord2);
        GeneralStatisticsReport report =(GeneralStatisticsReport) generalStatistics.getReport();

        // Assert
        assertEquals(fileName, report.fileNames().getFirst());
        assertEquals(startDate, report.startData());
        assertEquals(endDate, report.endData());
        assertEquals(2, report.requestCount());
        assertEquals(1500, report.averageResponseSize());
        assertEquals(1950.0, report.percentiles95());

    }

    @Test
    void emptyStatistics() {
        // Arrange
        String fileName = "file1";
        LocalDateTime startDate = LocalDateTime.of(2024, 6, 15, 12, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 6, 15, 12, 0);
        GeneralStatistics generalStatistics = new GeneralStatistics(
            List.of(fileName),
            startDate,
            endDate
        );

        // Act
        GeneralStatisticsReport report =(GeneralStatisticsReport) generalStatistics.getReport();

        // Assert
        assertEquals(fileName, report.fileNames().getFirst());
        assertEquals(startDate, report.startData());
        assertEquals(endDate, report.endData());
        assertEquals(0, report.requestCount());
        assertEquals(0, report.averageResponseSize());
        assertEquals(0, report.percentiles95());

    }
}
