package backend.academy;

import backend.academy.command_line.CommandLineArgs;
import backend.academy.config.AppConfig;
import backend.academy.filters.LogFilter;
import backend.academy.output.ConsoleOutput;
import backend.academy.output.MarkdownFileOutput;
import backend.academy.output.StatisticsOutput;
import backend.academy.parser.CommandLineParser;
import backend.academy.parser.LogParser;
import backend.academy.readers.HTTPReader;
import backend.academy.readers.LogFileReader;
import backend.academy.readers.Reader;
import backend.academy.statistics.GeneralStatistics;
import backend.academy.statistics.HttpMethodStatistics;
import backend.academy.statistics.ResourcesStatistics;
import backend.academy.statistics.ResponseCodesStatistics;
import backend.academy.statistics.Statistics;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import com.beust.jcommander.JCommander;
import lombok.experimental.UtilityClass;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder()
            .addObject(commandLineArgs)
            .build()
            .parse(args);

        CommandLineParser commandLineParser = new CommandLineParser(commandLineArgs);
        AppConfig appConfig = commandLineParser.parseCommandLine();

        List<Statistics> statistics =
            List.of(new GeneralStatistics(appConfig.inputFilePath(),appConfig.startDate(),appConfig.endDate()), new ResourcesStatistics(), new ResponseCodesStatistics(), new HttpMethodStatistics());
        Reader logFileReader = new LogFileReader(appConfig.inputFilePath());
//        Reader HttpReader = new HTTPReader(url);
        LogFilter logFilter = new LogFilter(appConfig);
        logFileReader.readLogs()
            .map(LogParser::parseLogLine)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(logFilter::filter)
            .forEach(logRecord ->statistics.forEach(statistic -> statistic.updateStatistics(logRecord)));


        StatisticsOutput statisticsOutput = new ConsoleOutput();
        statisticsOutput.print(statistics, System.out);

//        try (PrintStream out = new PrintStream("output.md")) {
//            StatisticsOutput statisticsOutput = new MarkdownFileOutput();
//            statisticsOutput.print(statistics, out);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
