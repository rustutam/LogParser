package backend.academy.output;

import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import java.io.PrintStream;

public class ConsoleOutput extends StatisticsOutput {

    @Override
    protected void printGeneralStatistics(Report statisticReport, PrintStream out) {
        GeneralStatisticsReport report = (GeneralStatisticsReport) statisticReport;
        out.println("ОБЩАЯ ИНФОРМАЦИЯ");
        out.println();
        out.println("Файл(-ы): " + report.fileName());
        out.println("Начальная дата: " + report.startData());
        out.println("Конечная дата: " + report.endData());
        out.println("Количество запросов: " + report.requestCount());
        out.println("Средний размер ответа: " + report.averageResponseSize());
        out.println("95p размера ответа: " + report.percentiles95());
        out.println();
        out.println();
    }

    @Override
    protected void printResourcesStatistics(Report statisticReport, PrintStream out) {
        ResourcesStatisticsReport report = (ResourcesStatisticsReport) statisticReport;
        out.println("ЗАПРАШИВАЕМЫЕ РЕСУРСЫ");
        out.println();
        report.resources()
            .forEach((resource, count) -> out.println(resource + ": " + count));
        out.println();
        out.println();

    }

    @Override
    protected void printResponseCodesStatistics(Report statisticReport, PrintStream out) {
        ResponseCodesStatisticsReport report = (ResponseCodesStatisticsReport) statisticReport;
        out.println("КОДЫ ОТВЕТА");
        out.println();

        report.responseCodes().forEach((code, count) ->
            out.println(code.value() + " (" + code.description() + ") : " + count)
        );
        out.println();
        out.println();

    }

}
