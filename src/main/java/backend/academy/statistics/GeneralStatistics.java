package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.Report;
import com.google.common.math.Quantiles;
import com.google.common.math.Stats;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class GeneralStatistics implements Statistics {
    private static final double ROUND_NUM = 100.0;
    private static final int PERCENTILE = 95;
    private final List<String> fileNames;
    private final LocalDateTime startData;
    private final LocalDateTime endData;
    private final List<Double> responseSizes = new ArrayList<>();
    private double percentile95 = 0;
    private double mean = 0;
    private int requestCount = 0;

    public GeneralStatistics(
        List<String> fileNames,
        LocalDateTime startData,
        LocalDateTime endData
    ) {
        this.fileNames = fileNames;
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
            percentile95 = round(Quantiles.percentiles().index(PERCENTILE).compute(responseSizes));
            mean = round(Stats.meanOf(responseSizes));
        }

        return new GeneralStatisticsReport(
            fileNames,
            startData,
            endData,
            requestCount,
            mean,
            percentile95
        );
    }

    private double round(double value) {
        return Math.round(value * ROUND_NUM) / ROUND_NUM;
    }
}
