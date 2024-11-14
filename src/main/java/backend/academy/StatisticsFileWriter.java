package backend.academy;

import backend.academy.output.StatisticsOutput;
import backend.academy.statistics.Statistics;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StatisticsFileWriter {
    private StatisticsFileWriter() {
    }

    public static void writeStatisticsToFile(
            StatisticsOutput statisticsOutput,
            List<Statistics> statistics,
            PrintStream out
    ) {
        String filePath = statisticsOutput.fileName();
        try (PrintStream printStream = new PrintStream(filePath, StandardCharsets.UTF_8)) {
            statisticsOutput.print(statistics, printStream);
        } catch (Exception e) {
            out.println("Ошибка записи в файл: " + filePath + "\n" + e.getMessage());
        }
    }
}
