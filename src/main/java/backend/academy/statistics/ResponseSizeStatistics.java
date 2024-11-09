package backend.academy.statistics;

import backend.academy.model.LogRecord;

public class ResponseSizeStatistics implements Statistics{
    private long count = 0;
    private long totalSize= 0;
    @Override
    public void updateStatistics(LogRecord logRecord) {
        long bodyBytesSize = logRecord.bodyBytesSize();

        count +=1;
        totalSize += bodyBytesSize;
    }

    public long getAverageSize(){
        return totalSize/count;
    }
}
