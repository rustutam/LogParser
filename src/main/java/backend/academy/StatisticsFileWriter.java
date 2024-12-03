package backend.academy;

import backend.academy.config.Settings;
import backend.academy.output.StatisticsOutput;
import backend.academy.statistics.Statistics;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Класс для записи статистики в файл.
 */
@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsFileWriter {

    /**
     * Записывает статистику в файл.
     *
     * @param statisticsOutput объект, содержащий информацию о выходных данных статистики
     * @param statistics список объектов статистики для записи
     */
    public static void writeStatisticsToFile(
        StatisticsOutput statisticsOutput,
        List<Statistics> statistics
    ) {
        try {
            String outputBaseDirectory = Settings.OUTPUT_BASE_DIRECTORY;
            Path baseDirPath = Paths.get(outputBaseDirectory);
            if (!Files.exists(baseDirPath)) {
                Files.createDirectories(baseDirPath);
            }

            String filePath = outputBaseDirectory + File.separator + statisticsOutput.fileName();
            try (PrintStream printStream = new PrintStream(filePath, StandardCharsets.UTF_8)) {
                statisticsOutput.print(statistics, printStream);
            }
        } catch (Exception e) {
            log.error("Ошибка записи в файл: {}\n{}", statisticsOutput.fileName(), e.getMessage());
        }
    }
}
