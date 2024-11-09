package backend.academy.config;

import java.time.ZonedDateTime;
import java.util.Map;

//Хранит параметры, полученные из командной строки, и предоставляет к ним доступ.
public record AppConfig (
    String inputFilePath,
    ZonedDateTime startDate,
    ZonedDateTime endDate,
    String format,
    Map<String, Object> filters
) {
}
