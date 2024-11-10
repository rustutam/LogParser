package backend.academy.output;

import backend.academy.config.Settings;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class AsciiDocFileOutput extends StatisticsOutput {

    @Override
    protected void printGeneralStatistics(Report statisticReport, PrintStream out) {
        GeneralStatisticsReport report = (GeneralStatisticsReport) statisticReport;
        String startData = report.startData().map(LocalDateTime::toString).orElse("-");
        String endData = report.endData().map(LocalDateTime::toString).orElse("-");

        out.println("== General Information");
        out.println();
        out.println("| Metric | Value |");
        out.println("|--------|-------|");
        out.println("| File(s) | " + report.fileName() + " |");
        out.println("| Start Date | " + startData + " |");
        out.println("| End Date | " + endData + " |");
        out.println("| Request Count | " + report.requestCount() + " |");
        out.println("| Average Response Size | " + report.averageResponseSize() + "b |");
        out.println("| 95th Percentile Response Size | " + report.percentiles95() + "b |");
        out.println();
    }

    @Override
    protected void printResourcesStatistics(Report statisticReport, PrintStream out) {
        ResourcesStatisticsReport report = (ResourcesStatisticsReport) statisticReport;

        out.println("== Requested Resources");
        out.println();
        out.println("| Resource | Count |");
        out.println("|----------|-------|");
        getTopNElements(report.resources(), Settings.FILTER_VALUE)
            .forEach((resource, count) -> out.println("| " + resource + " | " + count + " |"));
        out.println();
    }

    @Override
    protected void printResponseCodesStatistics(Report statisticReport, PrintStream out) {
        ResponseCodesStatisticsReport report = (ResponseCodesStatisticsReport) statisticReport;

        out.println("== Response Codes");
        out.println();
        out.println("| Code | Description | Count |");
        out.println("|------|-------------|-------|");
        getTopNElements(report.responseCodes(), Settings.FILTER_VALUE)
            .forEach((code, count) ->
                out.println("| " + code.value() + " | " + code.description() + " | " + count + " |")
            );
        out.println();
    }
}
