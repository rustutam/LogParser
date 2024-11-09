package backend.academy.model;

import java.time.ZonedDateTime;

public record LogRecord(
    String ip,
    String user,
    ZonedDateTime timeLocal,
    String requestMethod,
    String requestResource,
    String protocolVersion,
    int responseCode,
    long bodyBytesSize,
    String referer,
    String userAgent
) {

}
