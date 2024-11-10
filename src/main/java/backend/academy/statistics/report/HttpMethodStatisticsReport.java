package backend.academy.statistics.report;

import java.util.HashMap;

public record HttpMethodStatisticsReport(
    String statisticsName,
    HashMap<String, Integer> methods
) implements Report {

}
