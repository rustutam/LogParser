package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.Report;
import com.google.common.math.Quantiles;
import com.google.common.math.Stats;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class GeneralStatistics implements Statistics {
    private final String fileName;
    private final Optional<LocalDateTime> startData;
    private final Optional<LocalDateTime> endData;
    private final List<Double> responseSizes = new ArrayList<>();
    private double percentile95 = 0;
    private double mean = 0;
    private int requestCount = 0;

    public GeneralStatistics(String fileName, Optional<LocalDateTime> startData, Optional<LocalDateTime> endData) {
        this.fileName = fileName;
        this.startData = startData;
        this.endData = endData;
    }

    @Override
    public void updateStatistics(LogRecord logRecord) {
        requestCount += 1;

        double responseSize = logRecord.bodyBytesSize();
        responseSizes.add(responseSize);
    }

    @Override
    public Report getReport() {
        if (!responseSizes.isEmpty()) {
            percentile95 = round(Quantiles.percentiles().index(95).compute(responseSizes));
            mean = round(Stats.meanOf(responseSizes));
        }

        return new GeneralStatisticsReport(
            fileName,
            startData,
            endData,
            requestCount,
            mean,
            percentile95
        );
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
