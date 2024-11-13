package backend.academy.output;

import backend.academy.config.Settings;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class ConsoleOutput extends StatisticsOutput {

    @Override
    protected void printGeneralStatistics(GeneralStatisticsReport report, PrintStream out) {
        String startData = report.startData().map(LocalDateTime::toString).orElse("-");
        String endData = report.endData().map(LocalDateTime::toString).orElse("-");
        out.println("ОБЩАЯ ИНФОРМАЦИЯ");
        out.println();
        out.println("Файл(-ы): " + report.fileName());
        out.println("Начальная дата: " + startData);
        out.println("Конечная дата: " + endData);
        out.println("Количество запросов: " + report.requestCount());
        out.println("Средний размер ответа: " + report.averageResponseSize() + "b");
        out.println("95p размера ответа: " + report.percentiles95() + "b");
        out.println();
        out.println();
    }

    @Override
    protected void printResourcesStatistics(ResourcesStatisticsReport report, PrintStream out) {
        out.println("ЗАПРАШИВАЕМЫЕ РЕСУРСЫ");
        out.println();
        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println(resource + ": " + count));
        out.println();
        out.println();

    }

    @Override
    protected void printResponseCodesStatistics(ResponseCodesStatisticsReport report, PrintStream out) {
        out.println("КОДЫ ОТВЕТА");
        out.println();

        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((code, count) ->
                out.println(code.value() + " (" + code.description() + ") : " + count)
            );
        out.println();
        out.println();

    }

    @Override
    protected void printHttpMethodStatistics(HttpMethodStatisticsReport report, PrintStream out) {
        out.println("МЕТОДЫ HTTP");
        out.println();
        getTopNElements(report.statistics(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println(resource + ": " + count));
        out.println();
        out.println();
    }

}
