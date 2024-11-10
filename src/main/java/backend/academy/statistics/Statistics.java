package backend.academy.statistics;

import backend.academy.statistics.report.Report;
import backend.academy.model.LogRecord;

public interface Statistics {

    void updateStatistics(LogRecord logRecord);

    Report getReport();
}
