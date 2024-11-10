package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.Report;
import java.util.HashMap;

public class HttpMethodStatistics implements Statistics{
    private final String statisticsName = "Методы HTTP";
    private HashMap<String, Integer> methods = new HashMap<>();

    @Override
    public void updateStatistics(LogRecord logRecord) {
        String requestMethod = logRecord.request().requestMethod();

        if(methods.containsKey(requestMethod)){
            methods.put(requestMethod, methods.get(requestMethod) + 1);
        } else {
            methods.put(requestMethod, 1);
        }
    }
    @Override
    public Report getReport() {
        return new HttpMethodStatisticsReport(statisticsName, methods);
    }
}
