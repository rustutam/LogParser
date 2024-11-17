package backend.academy.output;

import backend.academy.model.HttpStatusCode;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.IpStatisticsReport;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AsciiDocFileOutputTest {

    private AsciiDocFileOutput asciiDocFileOutput;
    private ByteArrayOutputStream outputStream;
    private PrintStream out;

    @BeforeEach
    void setUp() {
        asciiDocFileOutput = new AsciiDocFileOutput();
        outputStream = new ByteArrayOutputStream();
        out = new PrintStream(outputStream);
    }

    @Test
    void printGeneralStatistics() {
        // Arrange
        GeneralStatisticsReport report = Mockito.mock(GeneralStatisticsReport.class);
        when(report.startData()).thenReturn(LocalDateTime.MIN);
        when(report.endData()).thenReturn(LocalDateTime.MAX);
        when(report.fileNames()).thenReturn(List.of("file1.log", "file2.log"));
        when(report.requestCount()).thenReturn(100);
        when(report.averageResponseSize()).thenReturn(1000.1);
        when(report.percentiles95()).thenReturn(950.0);

        String expectedOutput = """
            == Общая информация
            [cols="2,1", options="header"]
            |===
            | Метрика                | Значение
            |       Файл(-ы)         | file1.log, file2.log
            |    Начальная дата      | -
            |     Конечная дата      | -
            |  Количество запросов   | 100
            | Средний размер ответа  | 1000.1b
            |   95p размера ответа   | 950.0b
            |===
            """;

        // Act
        asciiDocFileOutput.printGeneralStatistics(report, out);

        // Assert
        String actualOutput = outputStream.toString().trim().replace("\r\n", "\n");
        String normalizedExpectedOutput = expectedOutput.trim().replace("\r\n", "\n");
        assertEquals(normalizedExpectedOutput, actualOutput);

    }

    @Test
    void printResourcesStatistics() {
        // Arrange
        ResourcesStatisticsReport report = Mockito.mock(ResourcesStatisticsReport.class);
        HashMap<String, Integer> stat = new HashMap<>(Map.of("resource1", 50, "resource2", 30));
        when(report.statistics()).thenReturn(stat);

        String expectedOutput = """
            == Запрашиваемые ресурсы
            [cols="2,1", options="header"]
            |===
            |     Ресурс      | Количество
            | resource1 | 50
            | resource2 | 30
            |===
            """;

        // Act
        asciiDocFileOutput.printResourcesStatistics(report, out);

        // Assert
        String actualOutput = outputStream.toString().trim().replace("\r\n", "\n");
        String normalizedExpectedOutput = expectedOutput.trim().replace("\r\n", "\n");
        assertEquals(normalizedExpectedOutput, actualOutput);

    }

    @Test
    void printResponseCodesStatistics() {
        // Arrange
        ResponseCodesStatisticsReport report = Mockito.mock(ResponseCodesStatisticsReport.class);
        HttpStatusCode code200 = HttpStatusCode.OK;
        HttpStatusCode code404 = HttpStatusCode.NOT_FOUND;
        HashMap<HttpStatusCode, Integer> stat = new HashMap<>(Map.of(code200, 70, code404, 20));
        when(report.statistics()).thenReturn(stat);

        String expectedOutput = """
            == Коды ответа
            [cols="1,2,1", options="header"]
            |===
            | Код |          Имя          | Количество
            | 200 | OK | 70
            | 404 | Not Found | 20
            |===
            """;

        // Act
        asciiDocFileOutput.printResponseCodesStatistics(report, out);

        // Assert
        String actualOutput = outputStream.toString().trim().replace("\r\n", "\n");
        String normalizedExpectedOutput = expectedOutput.trim().replace("\r\n", "\n");
        assertEquals(normalizedExpectedOutput, actualOutput);
    }

    @Test
    void printHttpMethodStatistics() {
        // Arrange
        HttpMethodStatisticsReport report = Mockito.mock(HttpMethodStatisticsReport.class);
        HashMap<String, Integer> stat = new HashMap<>(Map.of("GET", 80, "POST", 20));
        when(report.statistics()).thenReturn(stat);

        String expectedOutput = """
            == Методы Http
            [cols="2,1", options="header"]
            |===
            |     Метод      | Количество
            | GET | 80
            | POST | 20
            |===
            """;

        // Act
        asciiDocFileOutput.printHttpMethodStatistics(report, out);

        // Assert
        String actualOutput = outputStream.toString().trim().replace("\r\n", "\n");
        String normalizedExpectedOutput = expectedOutput.trim().replace("\r\n", "\n");
        assertEquals(normalizedExpectedOutput, actualOutput);
    }

    @Test
    void printIpStatistics() {
        // Arrange
        IpStatisticsReport report = Mockito.mock(IpStatisticsReport.class);
        HashMap<String, Integer> stat = new HashMap<>(Map.of("192.168.0.0", 80, "127.0.0.0", 20));
        when(report.statistics()).thenReturn(stat);

        String expectedOutput = """
            == Ip адреса
            [cols="2,1", options="header"]
            |===
            |     Ip адрес      | Количество
            | 192.168.0.0 | 80
            | 127.0.0.0 | 20
            |===
            """;

        // Act
        asciiDocFileOutput.printIpStatistics(report, out);

        // Assert
        String actualOutput = outputStream.toString().trim().replace("\r\n", "\n");
        String normalizedExpectedOutput = expectedOutput.trim().replace("\r\n", "\n");
        assertEquals(normalizedExpectedOutput, actualOutput);
    }
}
