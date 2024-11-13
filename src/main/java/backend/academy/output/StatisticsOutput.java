package backend.academy.output;

import backend.academy.statistics.GeneralStatistics;
import backend.academy.statistics.report.GeneralStatisticsReport;
import backend.academy.statistics.report.HttpMethodStatisticsReport;
import backend.academy.statistics.report.Report;
import backend.academy.statistics.Statistics;
import backend.academy.statistics.report.ResourcesStatisticsReport;
import backend.academy.statistics.report.ResponseCodesStatisticsReport;
import lombok.Getter;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public abstract class StatisticsOutput {
    private String fileName;

    public void print(List<Statistics> statisticsList, PrintStream out) {
        for (Statistics statistics : statisticsList) {
            Report staticticsReport = statistics.getReport();
            switch (staticticsReport) {
                case GeneralStatisticsReport generalStatisticsReport -> {
                    printGeneralStatistics(generalStatisticsReport, out);
                }
                case ResourcesStatisticsReport resourcesStatisticsReport -> {
                    printResourcesStatistics(resourcesStatisticsReport, out);
                }
                case ResponseCodesStatisticsReport responseCodesStatisticsReport -> {
                    printResponseCodesStatistics(responseCodesStatisticsReport, out);
                }
                case HttpMethodStatisticsReport httpMethodStatisticsReport -> {
                    printHttpMethodStatistics(httpMethodStatisticsReport, out);
                }
            }
        }
    }

    protected void printHttpMethodStatistics(HttpMethodStatisticsReport statisticsReport, PrintStream out) {
    }

    protected void printGeneralStatistics(GeneralStatisticsReport statisticsReport, PrintStream out) {
    }

    protected void printResourcesStatistics(ResourcesStatisticsReport statisticsReport, PrintStream out) {
    }

    protected void printResponseCodesStatistics(ResponseCodesStatisticsReport statisticsReport, PrintStream out) {
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
