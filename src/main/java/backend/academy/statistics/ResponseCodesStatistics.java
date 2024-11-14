package backend.academy.statistics;

import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;

public final class ResponseCodesStatistics extends NameCountStatistics<HttpStatusCode> {
    @Override
    protected HttpStatusCode extractKey(LogRecord logRecord) {
        return logRecord.responseCode();
    }

    @Override
    public Report getReport() {
        return new ResponseCodesStatisticsReport(statistics);
    }
}
