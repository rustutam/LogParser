package backend.academy.output;

import backend.academy.config.Settings;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.IpStatisticsReport;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import java.io.PrintStream;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AsciiDocFileOutput extends StatisticsOutput {
    private static final String COLS_2_1_OPTIONS_HEADER = "[cols=\"2,1\", options=\"header\"]";
    private static final String COLS_1_2_1_OPTIONS_HEADER = "[cols=\"1,2,1\", options=\"header\"]";
    private static final String ASCII_TABLE_FORMAT = "|===";
    private static final String SEPARATOR = " | ";

    public AsciiDocFileOutput() {
        this.fileName = "report.adoc";
    }

    @Override
    protected void printGeneralStatistics(GeneralStatisticsReport report, PrintStream out) {
        String startData = report.startData().equals(LocalDateTime.MIN) ? "-" : report.startData().toString();
        String endData = report.endData().equals(LocalDateTime.MAX) ? "-" : report.startData().toString();
        String sizeUnit = "b";

        out.println("== Общая информация");
        out.println(COLS_2_1_OPTIONS_HEADER);
        out.println(ASCII_TABLE_FORMAT);
        out.println("| Метрика                | Значение");
        out.println("|       Файл(-ы)         | " + String.join(", ", report.fileNames()));
        out.println("|    Начальная дата      | " + startData);
        out.println("|     Конечная дата      | " + endData);
        out.println("|  Количество запросов   | " + report.requestCount());
        out.println("| Средний размер ответа  | " + report.averageResponseSize() + sizeUnit);
        out.println("|   95p размера ответа   | " + report.percentiles95() + sizeUnit);
        out.println(ASCII_TABLE_FORMAT);

    }

    @Override
    protected void printResourcesStatistics(ResourcesStatisticsReport report, PrintStream out) {
        out.println("== Запрашиваемые ресурсы");
        out.println(COLS_2_1_OPTIONS_HEADER);
        out.println(ASCII_TABLE_FORMAT);
        out.println("|     Ресурс      | Количество");
        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println("| " + resource + SEPARATOR + count));
        out.println(ASCII_TABLE_FORMAT);
    }

    @Override
    protected void printResponseCodesStatistics(ResponseCodesStatisticsReport report, PrintStream out) {
        out.println("== Коды ответа");
        out.println(COLS_1_2_1_OPTIONS_HEADER);
        out.println(ASCII_TABLE_FORMAT);
        out.println("| Код |          Имя          | Количество");
        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((code, count) ->
                out.println("| " + code.value() + SEPARATOR + code.description() + SEPARATOR + count)
            );
        out.println(ASCII_TABLE_FORMAT);
    }

    @Override
    protected void printHttpMethodStatistics(HttpMethodStatisticsReport report, PrintStream out) {
        out.println("== Методы Http");
        out.println(COLS_2_1_OPTIONS_HEADER);
        out.println(ASCII_TABLE_FORMAT);
        out.println("|     Метод      | Количество");
        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println("| " + resource + SEPARATOR + count));
        out.println(ASCII_TABLE_FORMAT);
    }

    @Override
    protected void printIpStatistics(IpStatisticsReport report, PrintStream out) {
        out.println("== Ip адреса");
        out.println(COLS_2_1_OPTIONS_HEADER);
        out.println(ASCII_TABLE_FORMAT);
        out.println("|     Ip адрес      | Количество");
        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println("| " + resource + SEPARATOR + count));
        out.println(ASCII_TABLE_FORMAT);
    }
}
