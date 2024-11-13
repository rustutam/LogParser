package backend.academy.statistics.report;

import java.time.LocalDateTime;
import java.util.Optional;

public record GeneralStatisticsReport(
    String fileName,
    Optional<LocalDateTime> startData,
    Optional<LocalDateTime> endData,
    int requestCount,
    double averageResponseSize,
    double percentiles95
) implements Report {
}
