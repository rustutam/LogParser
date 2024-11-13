package backend.academy.statistics.report;

import java.util.HashMap;

public record HttpMethodStatisticsReport(
    HashMap<String, Integer> statistics
) implements Report {

}
