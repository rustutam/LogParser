package backend.academy;

import backend.academy.config.AppConfig;
import backend.academy.filters.LogFilter;
import backend.academy.model.Format;
import backend.academy.output.AsciiDocFileOutput;
import backend.academy.output.MarkdownFileOutput;
import backend.academy.output.StatisticsOutput;
import backend.academy.parser.LogParser;
import backend.academy.readers.HTTPReader;
import backend.academy.readers.LogFileReader;
import backend.academy.readers.Reader;
import backend.academy.statistics.GeneralStatistics;
import backend.academy.statistics.HttpMethodStatistics;
import backend.academy.statistics.ResourcesStatistics;
import backend.academy.statistics.ResponseCodesStatistics;
import backend.academy.statistics.Statistics;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

public class App {
    private final AppConfig appConfig;
    private final PrintStream out;

    public App(AppConfig appConfig, PrintStream out) {
        this.appConfig = appConfig;
        this.out = out;
    }

    public void run() {
        FileData fileData = getFileData(appConfig.inputFilePath());
        List<Statistics> statistics =
            List.of(
                new GeneralStatistics(fileData.fileNames(), appConfig.startDate(), appConfig.endDate()),
                new ResourcesStatistics(),
                new ResponseCodesStatistics(),
                new HttpMethodStatistics()
            );

        LogFilter logFilter = new LogFilter(appConfig);

        Reader reader = fileData.reader();
        reader.readLogs(fileData.filePaths())
            .map(LogParser::parseLogLine)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(logFilter::filter)
            .forEach(logRecord -> statistics.forEach(statistic -> statistic.updateStatistics(logRecord)));

        StatisticsFileWriter.writeStatisticsToFile(getOutput(appConfig.format()), statistics, out);
    }

    private FileData getFileData(String path) {
        if (URLChecker.isValidURL(path)) {
            return new FileData(
                List.of(path),
                new HTTPReader(),
                List.of(path)
            );
        } else {
            PathProcessor pathProcessor = new PathProcessor(appConfig.inputFilePath(), out);
            return new FileData(
                pathProcessor.getFileNames(),
                new LogFileReader(),
                pathProcessor.getFilePaths()
            );

        }
    }

    private StatisticsOutput getOutput(Format format) {
        return switch (format) {
            case MD -> new MarkdownFileOutput();
            case ADOC -> new AsciiDocFileOutput();
        };
    }

}
