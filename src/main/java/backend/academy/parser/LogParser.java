package backend.academy.parser;

import backend.academy.config.Settings;
import backend.academy.model.HttpStatusCode;
import backend.academy.model.LogRecord;
import backend.academy.model.RequestModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static java.lang.Integer.parseInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static Optional<LogRecord> parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (matcher.matches()) {
            try {
                String ip = matcher.group(Settings.Group.IP.value());
                String user = matcher.group(Settings.Group.USER.value());
                LocalDateTime dateTime =
                    LocalDateTime.parse(matcher.group(Settings.Group.DATE_TIME.value()), DATE_FORMATTER);
                String requestMethod = matcher.group(Settings.Group.REQUEST_METHOD.value());
                String resource = matcher.group(Settings.Group.RESOURCE.value());
                String protocolVersion = matcher.group(Settings.Group.PROTOCOL_VERSION.value());

                RequestModel request = new RequestModel(requestMethod, resource, protocolVersion);

                HttpStatusCode responseCode =
                    HttpStatusCode.getByValue(parseInt(matcher.group(Settings.Group.RESPONSE_CODE.value())));
                long bodyBytesSent = Long.parseLong(matcher.group(Settings.Group.BODY_BYTES_SENT.value()));
                String referer = matcher.group(Settings.Group.REFERER.value());
                String userAgent = matcher.group(Settings.Group.USER_AGENT.value());

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

