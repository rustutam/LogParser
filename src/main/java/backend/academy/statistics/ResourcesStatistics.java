package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import lombok.Getter;

@Getter public final class ResourcesStatistics extends NameCountStatistics<String> {
    @Override
    protected String extractKey(LogRecord logRecord) {
        return logRecord.request().requestResource();
    }

    @Override
    public Report getReport() {
        return new ResourcesStatisticsReport(statistics);
    }
}
