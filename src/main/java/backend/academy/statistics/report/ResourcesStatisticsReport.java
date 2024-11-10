package backend.academy.statistics.report;

import java.util.HashMap;

public record ResourcesStatisticsReport(
    String statisticsName,
    HashMap<String, Integer> resources
) implements Report{

}
