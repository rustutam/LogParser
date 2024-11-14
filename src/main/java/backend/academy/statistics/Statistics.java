package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.Report;

public sealed interface Statistics permits GeneralStatistics, NameCountStatistics {

    void updateStatistics(LogRecord logRecord);

    Report getReport();
}
