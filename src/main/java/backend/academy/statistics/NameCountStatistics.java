package backend.academy.statistics;

import backend.academy.model.LogRecord;
import java.util.HashMap;
import lombok.Getter;

@Getter
public abstract sealed class NameCountStatistics<T> implements Statistics
    permits HttpMethodStatistics, ResourcesStatistics, ResponseCodesStatistics, IpStatistics {
    protected HashMap<T, Integer> statistics = new HashMap<>();

    @Override
    public void updateStatistics(LogRecord logRecord) {
        T key = extractKey(logRecord);
        statistics.put(key, statistics.getOrDefault(key, 0) + 1);
    }

    protected abstract T extractKey(LogRecord logRecord);

}
