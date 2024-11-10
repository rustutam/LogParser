package backend.academy.output;

import backend.academy.report.Report;
import backend.academy.statistics.Statistics;
import java.io.PrintStream;
import java.util.List;

public abstract class StatisticsOutput {
    protected final int filterValue = 3;

    public void print(List<Statistics> statisticsList, PrintStream out) {
        for (Statistics statistics : statisticsList) {
            Report staticticReport = statistics.getReport();
            switch (staticticReport.getClass().getSimpleName()) {
                case "GeneralStatisticsReport" -> {
                    printGeneralStatistics(staticticReport, out);
                }
                case "ResourcesStatisticsReport" -> {
                    printResourcesStatistics(staticticReport, out);
                }
                case "ResponseCodesStatisticsReport" -> {
                    printResponseCodesStatistics(staticticReport, out);
                }
            }
        }
    }

    protected void printGeneralStatistics(Report statisticReport, PrintStream out) {

    }
    protected void printResourcesStatistics(Report statisticReport, PrintStream out) {

    }
    protected void printResponseCodesStatistics(Report statisticReport, PrintStream out) {

    }
}
