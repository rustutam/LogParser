package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.IpStatisticsReport;
import backend.academy.statistics.report.Report;

public final class IpStatistics extends NameCountStatistics<String> {
    @Override
    protected String extractKey(LogRecord logRecord) {
        return logRecord.ip();
    }

    @Override
    public Report getReport() {
        return new IpStatisticsReport(statistics);
    }
}
