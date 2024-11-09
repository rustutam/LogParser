package backend.academy.statistics;

import backend.academy.model.LogRecord;
import lombok.Getter;
import java.util.HashMap;

public class ResponseCodesStatistics implements Statistics{
    @Getter
    private HashMap<Integer, Integer> responseCodes = new HashMap<>();
    @Override
    public void updateStatistics(LogRecord logRecord) {
        int responseCode = logRecord.responseCode();

        if(responseCodes.containsKey(responseCode)){
            responseCodes.put(responseCode, responseCodes.get(responseCode) + 1);
        } else {
            responseCodes.put(responseCode, 1);
        }




    }
}
