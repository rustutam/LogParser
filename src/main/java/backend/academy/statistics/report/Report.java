package backend.academy.statistics.report;

public sealed interface Report
    permits GeneralStatisticsReport, HttpMethodStatisticsReport, ResponseCodesStatisticsReport,
    ResourcesStatisticsReport {
}
