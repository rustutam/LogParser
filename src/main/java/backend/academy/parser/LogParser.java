package backend.academy.parser;
//Считывает строки из лога и преобразует их в объекты LogRecord.
//Поддерживает потоковую обработку, чтобы избежать загрузки всего файла в память.

import backend.academy.model.LogRecord;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;

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
     * @return LogRecord или null, если строка не соответствует формату
     */
    public static LogRecord parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (matcher.matches()) {
            try {
                String ip = matcher.group(1);
                String user = matcher.group(2);
                ZonedDateTime dateTime = ZonedDateTime.parse(matcher.group(3), DATE_FORMATTER);
                String requestMethod = matcher.group(4);
                String resource = matcher.group(5);
                String protocolVersion = matcher.group(6);
                int responseCode = Integer.parseInt(matcher.group(7));
                long bodyBytesSent = Long.parseLong(matcher.group(8));
                String referer = matcher.group(9);
                String userAgent = matcher.group(10);

                return new LogRecord(
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
                );
            } catch (Exception e) {
                log.error("Ошибка при парсинге строки: {} в классе {}", logLine, e.getClass());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String str =
            "80.91.33.133 - - [17/May/2015:09:05:33 +0002] \"GET /downloads/product_1 HTTP/1.1\" 404 324 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"";
        LogParser logParser = new LogParser();
        LogRecord logRecord = logParser.parseLogLine(str);
    }
//
//    /**
//     * Метод для чтения логов из локального файла с поддержкой потоковой обработки
//     *
//     * @param filePath путь к файлу
//     * @return Stream<LogRecord> поток записей логов
//     * @throws IOException если произошла ошибка чтения файла
//     */
//    public Stream<LogRecord> parseLogFile(Path filePath) throws IOException {
//        return Files.lines(filePath)
//            .map(this::parseLogLine)
//            .filter(Objects::nonNull);
//    }
//
//    /**
//     * Метод для чтения логов с URL с поддержкой потоковой обработки
//     *
//     * @param url URL лог-файла
//     * @return Stream<LogRecord> поток записей логов
//     * @throws IOException если произошла ошибка загрузки файла
//     */
//    public Stream<LogRecord> parseLogFromURL(URL url) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        return reader.lines()
//            .map(this::parseLogLine)
//            .filter(record -> record != null);
//    }
}

