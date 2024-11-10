package backend.academy.report;

import java.util.HashMap;

public record ResourcesStatisticsReport(
    String statisticsName,
    HashMap<String, Integer> resources
) implements Report{

}
