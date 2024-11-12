package backend.academy.filters;

import java.time.LocalDateTime;
import static backend.academy.config.Settings.INPUT_DATA_FORMATTER;

public class DataFilterField extends FilterField<LocalDateTime> {
    @Override
    public void parseValue(String value) {
        super.value = LocalDateTime.parse(value, INPUT_DATA_FORMATTER);
    }

}
