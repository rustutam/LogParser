package backend.academy.statistics.report;

import backend.academy.HttpStatusCode;
import java.util.HashMap;

public record ResponseCodesStatisticsReport(
    HashMap<HttpStatusCode, Integer> statistics
) implements Report {

}

