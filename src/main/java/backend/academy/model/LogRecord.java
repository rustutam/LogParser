package backend.academy.model;

import backend.academy.HttpStatusCode;
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
