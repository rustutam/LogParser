package backend.academy.statistics;

import backend.academy.report.Report;
import backend.academy.model.LogRecord;

public interface Statistics {

    void updateStatistics(LogRecord logRecord);

    Report getReport();
}
