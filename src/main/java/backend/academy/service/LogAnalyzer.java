package backend.academy.service;

import backend.academy.config.AppConfig;
import backend.academy.model.LogRecord;
import backend.academy.parser.LogParser;
import backend.academy.statistics.Statistics;
import lombok.Getter;
import java.time.ZonedDateTime;
import java.util.List;

//Читает лог-файлы, используя LogParser.
//Фильтрует записи по временным параметрам from и to.
//Считает статистику: общее количество запросов, популярные ресурсы, коды ответов, средний размер ответа, 95-й перцентиль размера.
//Заполняет объект LogStatistics.
public class LogAnalyzer {
    @Getter
    private List<Statistics> statistics;

    public LogAnalyzer(List<Statistics> statistics) {
        this.statistics = statistics;
    }

    public void analyze(LogRecord logRecord) {
        statistics.forEach(statistics -> statistics.updateStatistics(logRecord));
    }

//    private boolean isWithinDateRange(ZonedDateTime logDate) {
//        ZonedDateTime startDate = appConfig.startDate();
//        ZonedDateTime endDate = appConfig.endDate();
//
//        if (startDate != null && endDate != null) {
//            return !logDate.isBefore(startDate) && !logDate.isAfter(endDate);
//        } else if (startDate != null) {
//            return !logDate.isBefore(startDate);
//        } else if (endDate != null) {
//            return !logDate.isAfter(endDate);
//        }
//        return true;
//    }

}

//TODO: фильтрация по агентам,методам запросов,кодами ответов, протоколами,датам,айпи,
