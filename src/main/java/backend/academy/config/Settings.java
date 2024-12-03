package backend.academy.config;

import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Settings {
    public static final int FILTER_VALUE = 3;
    public static final DateTimeFormatter INPUT_DATA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String OUTPUT_BASE_DIRECTORY = "src/main/resources/statistics";

    @Getter
    @AllArgsConstructor
    public enum Group {
        IP(1),
        USER(2),
        DATE_TIME(3),
        REQUEST_METHOD(4),
        RESOURCE(5),
        PROTOCOL_VERSION(6),
        RESPONSE_CODE(7),
        BODY_BYTES_SENT(8),
        REFERER(9),
        USER_AGENT(10);

        private final int value;
    }
}
