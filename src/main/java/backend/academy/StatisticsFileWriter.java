package backend.academy;

import backend.academy.output.StatisticsOutput;
import backend.academy.statistics.Statistics;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StatisticsFileWriter {
    public static void writeStatisticsToFile(StatisticsOutput statisticsOutput, List<Statistics> statistics,PrintStream out) {
        String filePath = statisticsOutput.fileName();
        try (PrintStream printStream = new PrintStream(filePath)) {
            statisticsOutput.print(statistics, printStream);
        } catch (FileNotFoundException e) {
            out.println("Ошибка записи в файл: "+ filePath+ "\n" + e.getMessage());
        }
    }
}
