package backend.academy.statistics;

import backend.academy.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.report.Report;
import backend.academy.report.ResponseCodesStatisticsReport;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class ResponseCodesStatistics implements Statistics {
    private static final String statisticsName = "Коды ответа";
    private final HashMap<HttpStatusCode, Integer> responseCodes = new HashMap<>();

    @Override
    public void updateStatistics(LogRecord logRecord) {
        HttpStatusCode responseCode = logRecord.responseCode();

        if (responseCodes.containsKey(responseCode)) {
            responseCodes.put(responseCode, responseCodes.get(responseCode) + 1);
        } else {
            responseCodes.put(responseCode, 1);
        }


    }

    @Override
    public Report getReport() {
        return new ResponseCodesStatisticsReport(statisticsName, responseCodes);
    }
}
