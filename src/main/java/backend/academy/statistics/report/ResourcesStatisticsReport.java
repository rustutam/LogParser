package backend.academy.statistics.report;

import java.util.HashMap;

public record ResourcesStatisticsReport(
    HashMap<String, Integer> statistics
) implements Report{

}
