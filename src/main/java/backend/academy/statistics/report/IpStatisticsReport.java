package backend.academy.statistics.report;

import java.util.HashMap;

public record IpStatisticsReport(
    HashMap<String, Integer> statistics
) implements Report {
}
