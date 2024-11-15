package backend.academy.model;

import backend.academy.exceptions.InvalidFilterFieldException;

public enum LogFilterField {
    IP("ip"),
    USER("user"),
    DATE("date"),
    REQUEST_METHOD("method"),
    REQUEST_RESOURCE("resource"),
    REQUEST_PROTOCOL_VERSION("protocol"),
    RESPONSE_CODE("code"),
    BODY_BYTES_SENT("bytes_size"),
    REFERER("referer"),
    USER_AGENT("agent");

    private final String value;

    LogFilterField(String value) {
        this.value = value;
    }

    public static LogFilterField getField(String value) throws InvalidFilterFieldException {
        for (LogFilterField filed : values()) {
            if (filed.value.equalsIgnoreCase(value)) {
                return filed;
            }
        }
        throw new InvalidFilterFieldException("Invalid field value: " + value);
    }
}


