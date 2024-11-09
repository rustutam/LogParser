package backend.academy;

import backend.academy.config.AppConfig;
import backend.academy.parser.LogParser;
import backend.academy.readers.HTTPReader;
import backend.academy.readers.LogFileReader;
import backend.academy.readers.Reader;
import backend.academy.service.LogAnalyzer;
import backend.academy.statistics.ResourcesStatistics;
import backend.academy.statistics.ResponseCodesStatistics;
import backend.academy.statistics.ResponseSizeStatistics;
import backend.academy.statistics.Statistics;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/logs.txt";
//        String url = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        AppConfig appConfig = new AppConfig(path, null, null, "common", null);
        List<Statistics> statistics =
            List.of(new ResourcesStatistics(), new ResponseCodesStatistics(), new ResponseSizeStatistics());
        LogAnalyzer logAnalyzer = new LogAnalyzer(statistics);
        Reader logFileReader = new LogFileReader(path);
//        Reader HttpReader = new HTTPReader(url);

        Stream<String> stringStream = logFileReader.readLogs();
        stringStream
            .map(LogParser::parseLogLine)
            .forEach(logRecord ->statistics.forEach(statistic -> statistic.updateStatistics(logRecord)));
        System.out.println(logAnalyzer.statistics().size());

    }
}
