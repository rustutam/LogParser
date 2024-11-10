package backend.academy.parser;

import backend.academy.HttpStatusCode;
import backend.academy.config.Settings;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import backend.academy.model.RequestModel;
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
                String ip = matcher.group(Settings.IP_GROUP);
                String user = matcher.group(Settings.USER_GROUP);
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(Settings.DATE_TIME_GROUP), DATE_FORMATTER);
                String requestMethod = matcher.group(Settings.REQUEST_METHOD_GROUP);
                String resource = matcher.group(Settings.RESOURCE_GROUP);
                String protocolVersion = matcher.group(Settings.PROTOCOL_VERSION_GROUP);

                RequestModel request = new RequestModel(requestMethod, resource, protocolVersion);

                HttpStatusCode responseCode = HttpStatusCode.getByValue(parseInt(matcher.group(Settings.RESPONSE_CODE_GROUP)));
                long bodyBytesSent = Long.parseLong(matcher.group(Settings.BODY_BYTES_SENT_GROUP));
                String referer = matcher.group(Settings.REFERER_GROUP);
                String userAgent = matcher.group(Settings.USER_AGENT_GROUP);

                return Optional.of(new LogRecord(
                    ip,
                    user,
                    dateTime,
                    request,
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

