package backend.academy.output;

import backend.academy.config.Settings;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class MarkdownFileOutput extends StatisticsOutput{

    @Override
    protected void printGeneralStatistics(Report statisticReport, PrintStream out) {
        GeneralStatisticsReport report = (GeneralStatisticsReport) statisticReport;
        String startData = report.startData().map(LocalDateTime::toString).orElse("-");
        String endData = report.endData().map(LocalDateTime::toString).orElse("-");

        out.println("#### Общая информация");
        out.println();
        out.println("|        Метрика        |     Значение |");
        out.println("|:---------------------:|:-------------:|");
        out.println("|       Файл(-ы)        | " + report.fileName() + " |");
        out.println("|    Начальная дата     | " + startData + " |");
        out.println("|     Конечная дата     | " + endData + " |");
        out.println("|  Количество запросов  | " + report.requestCount() + " |");
        out.println("| Средний размер ответа | " + report.averageResponseSize() + "b |");
        out.println("|   95p размера ответа  | " + report.percentiles95() + "b |");
        out.println();
        out.println();
    }

    @Override
    protected void printResourcesStatistics(Report statisticReport, PrintStream out) {
        ResourcesStatisticsReport report = (ResourcesStatisticsReport) statisticReport;

        out.println("#### Запрашиваемые ресурсы");
        out.println();
        out.println("|     Ресурс      | Количество |");
        out.println("|:---------------:|-----------:|");
        getTopNElements(report.resources(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println("| " + resource + " | " + count + " |"));
        out.println();
        out.println();
    }

    @Override
    protected void printResponseCodesStatistics(Report statisticReport, PrintStream out) {
        ResponseCodesStatisticsReport report = (ResponseCodesStatisticsReport) statisticReport;

        out.println("#### Коды ответа");
        out.println();
        out.println("| Код |          Имя          | Количество |");
        out.println("|:---:|:---------------------:|-----------:|");
        getTopNElements(report.responseCodes(), Settings.FILTER_VALUE)
            .forEach((code, count) ->
            out.println("| " + code.value() + " | " + code.description() + " | " + count + " |")
        );
        out.println();
        out.println();
    }

    @Override protected void printHttpMethodStatistics(Report statisticReport, PrintStream out) {
        HttpMethodStatisticsReport report = (HttpMethodStatisticsReport) statisticReport;

        out.println("#### Методы Http");
        out.println();
        out.println("|     Метод      | Количество |");
        out.println("|:---------------:|-----------:|");
        getTopNElements(report.methods(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println("| " + resource + " | " + count + " |"));
        out.println();
        out.println();
    }
}
