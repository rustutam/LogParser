package backend.academy.output;

import backend.academy.statistics.report.Report;
import backend.academy.statistics.Statistics;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class StatisticsOutput {

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
                case "HttpMethodStatisticsReport" -> {
                    printHttpMethodStatistics(staticticReport, out);
                }
            }
        }
    }

    protected void printHttpMethodStatistics(Report staticticReport, PrintStream out) {

    }

    protected void printGeneralStatistics(Report statisticReport, PrintStream out) {

    }
    protected void printResourcesStatistics(Report statisticReport, PrintStream out) {

    }
    protected void printResponseCodesStatistics(Report statisticReport, PrintStream out) {

    }

    protected static <K, V extends Comparable<? super V>> Map<K, V> getTopNElements(Map<K, V> map, int n) {
        return map.entrySet()
            .stream()
            .sorted(Map.Entry.<K, V>comparingByValue().reversed())
            .limit(n)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }


}
