package backend.academy.statistics;

import backend.academy.model.LogRecord;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import java.util.HashMap;
import lombok.Getter;

@Getter public class ResourcesStatistics implements Statistics{
    private static final String statisticsName = "Запрашиваемые ресурсы";
    private HashMap<String, Integer> resources = new HashMap<>();

    @Override
    public void updateStatistics(LogRecord logRecord) {
        String requestResource = logRecord.request().requestResource();

        if(resources.containsKey(requestResource)){
            resources.put(requestResource, resources.get(requestResource) + 1);
        } else {
            resources.put(requestResource, 1);
        }

    }

    @Override
    public Report getReport() {
        return new ResourcesStatisticsReport(statisticsName, resources);
    }
}
