package backend.academy.model;

import java.time.LocalDateTime;

public record LogRecord(
    String ip,
    String user,
    LocalDateTime timeLocal,
    RequestModel request,
    HttpStatusCode responseCode,
    long bodyBytesSize,
    String referer,
    String userAgent
) {

}
