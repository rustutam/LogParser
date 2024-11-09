package backend.academy.statistics;

import backend.academy.model.LogRecord;
import lombok.Getter;
import java.util.HashMap;

public class ResourcesStatistics implements Statistics{
    @Getter
    private HashMap<String, Integer> resources = new HashMap<>();
    @Override
    public void updateStatistics(LogRecord logRecord) {
        String requestResource = logRecord.requestResource();

        if(resources.containsKey(requestResource)){
            resources.put(requestResource, resources.get(requestResource) + 1);
        } else {
            resources.put(requestResource, 1);
        }

    }
}
