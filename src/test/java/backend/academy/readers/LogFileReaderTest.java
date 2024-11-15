package backend.academy.readers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogFileReaderTest {

    private LogFileReader logFileReader = new LogFileReader();

    @AfterEach
    void tearDown() throws IOException {
        logFileReader.close();
    }

    @Test
    void testReadLogsSuccessfully() throws IOException {
        // Создаем временный файл с тестовыми данными
        Path tempFile = Files.createTempFile("test-log", ".log");
        Files.write(tempFile, List.of("line1", "line2", "line3"), StandardCharsets.UTF_8);

        // Читаем файл через LogFileReader
        try (Stream<String> lines = logFileReader.readLogs(tempFile.toString())) {
            List<String> result = lines.toList();

            // Проверяем, что строки прочитаны корректно
            assertEquals(3, result.size());
            assertEquals("line1", result.get(0));
            assertEquals("line2", result.get(1));
            assertEquals("line3", result.get(2));
        }

        // Удаляем временный файл
        logFileReader.close();
        Files.delete(tempFile);
    }

    @Test
    void testReadLogsFileNotFound() {
        String invalidPath = "non-existent-file.log";

        // Проверяем, что выбрасывается RuntimeException при отсутствии файла
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> logFileReader.readLogs(invalidPath).toList()
        );

        assertTrue(exception.getMessage().contains("Ошибка при чтении файла"));
    }
}
