package backend.academy.statistics;

import backend.academy.statistics.report.Report;
import backend.academy.model.LogRecord;

public sealed interface Statistics permits GeneralStatistics,NameCountStatistics{

    void updateStatistics(LogRecord logRecord);

    Report getReport();
}
