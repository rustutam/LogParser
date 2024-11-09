package backend.academy.model;

import backend.academy.HttpStatusCode;
import java.time.LocalDateTime;

public record LogRecord(
    String ip,
    String user,
    LocalDateTime timeLocal,
    String requestMethod,
    String requestResource,
    String protocolVersion,
    HttpStatusCode responseCode,
    long bodyBytesSize,
    String referer,
    String userAgent
) {

}
