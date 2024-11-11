package backend.academy.config;

import backend.academy.filters.LogFilterField;
import backend.academy.model.Format;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

//Хранит параметры, полученные из командной строки, и предоставляет к ним доступ.
public record AppConfig(
    String inputFilePath,
    Optional<LocalDateTime> startDate,
    Optional<LocalDateTime> endDate,
    Optional<Format> format,
    Map<LogFilterField, Object> filters
//    List<Function<LogRecord, Boolean>> filters
) {
//    List<Function<LogRecord, Boolean>> filters = List.of(
//        lr -> lr.ip().equalsIgnoreCase(value)
//    )
}
