package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.Report;

public final class HttpMethodStatistics extends NameCountStatistics<String> {
    @Override
    protected String extractKey(LogRecord logRecord) {
        return logRecord.request().requestMethod();
    }

    @Override
    public Report getReport() {
        return new HttpMethodStatisticsReport(statistics);
    }
}
