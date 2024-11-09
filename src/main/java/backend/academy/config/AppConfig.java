package backend.academy.config;

import backend.academy.filters.LogFilterField;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

//Хранит параметры, полученные из командной строки, и предоставляет к ним доступ.
public record AppConfig(
    String inputFilePath,
    Optional<LocalDateTime> startDate,
    Optional<LocalDateTime> endDate,
    Optional<String> format,
    Map<LogFilterField, Object> filters
) {
}
