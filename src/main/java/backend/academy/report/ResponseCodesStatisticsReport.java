package backend.academy.report;

import backend.academy.HttpStatusCode;
import java.util.HashMap;

public record ResponseCodesStatisticsReport(
    String statisticsName,
    HashMap<HttpStatusCode, Integer> responseCodes
) implements Report{

}

