package backend.academy.statistics.report;

import java.time.LocalDateTime;
import java.util.List;

public record GeneralStatisticsReport(
    List<String> fileNames,
    LocalDateTime startData,
    LocalDateTime endData,
    int requestCount,
    double averageResponseSize,
    double percentiles95
) implements Report {
}
