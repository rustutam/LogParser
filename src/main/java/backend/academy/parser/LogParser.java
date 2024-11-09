package backend.academy.parser;

import backend.academy.HttpStatusCode;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;
import static java.lang.Integer.parseInt;

@Log4j2
public class LogParser {

    // Регулярное выражение для парсинга строки лога в формате NGINX
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(\\S+) - (\\S+) \\[(.*?)] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+) \"(.*?)\" \"(.*?)\"$"
    );

    // Формат даты в логах NGINX
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
        "dd/MMM/yyyy:HH:mm:ss Z",
        Locale.ENGLISH);

    /**
     * Метод для парсинга строки лога в объект LogRecord
     *
     * @param logLine строка лога
     * @return Optional<LogRecord> or Optional.empty(), если строка не соответствует формату
     */
    public static Optional<LogRecord> parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (matcher.matches()) {
            try {
                String ip = matcher.group(1);
                String user = matcher.group(2);
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(3), DATE_FORMATTER);
                String requestMethod = matcher.group(4);
                String resource = matcher.group(5);
                String protocolVersion = matcher.group(6);
                HttpStatusCode responseCode = HttpStatusCode.getByValue(parseInt(matcher.group(7)));
                long bodyBytesSent = Long.parseLong(matcher.group(8));
                String referer = matcher.group(9);
                String userAgent = matcher.group(10);

                return Optional.of(new LogRecord(
                    ip,
                    user,
                    dateTime,
                    requestMethod,
                    resource,
                    protocolVersion,
                    responseCode,
                    bodyBytesSent,
                    referer,
                    userAgent
                ));
            } catch (Exception e) {
                log.error("Ошибка при парсинге строки: {} в классе {}", logLine, e.getClass());
            }
        }
        return Optional.empty();
    }
}

