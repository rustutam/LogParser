package backend.academy.config;

import backend.academy.filters.FilterField;
import backend.academy.model.Format;
import java.time.LocalDateTime;
import java.util.List;

//Хранит параметры, полученные из командной строки, и предоставляет к ним доступ.
public record AppConfig(
    String inputFilePath,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Format format,
    List<FilterField<?>> filterFieldList
) {

}
