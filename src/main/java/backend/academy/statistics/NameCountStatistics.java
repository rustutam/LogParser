package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.Report;
import lombok.Getter;
import java.util.HashMap;

@Getter
public sealed abstract class NameCountStatistics<T> implements Statistics
    permits HttpMethodStatistics, ResourcesStatistics, ResponseCodesStatistics
{
    protected HashMap<T, Integer> statistics = new HashMap<>();

    @Override
    public void updateStatistics(LogRecord logRecord){
        T key = extractKey(logRecord);
        statistics.put(key, statistics.getOrDefault(key, 0) + 1);
    };

    protected abstract T extractKey(LogRecord logRecord);

    @Override
    public abstract Report getReport();
}
