package backend.academy.filters;

import backend.academy.exceptions.InvalidFilterValueException;

public enum LogFilterField {
    IP("ip"),
    USER("user"),
    DATA("data"),
    REQUEST_METHOD("method"),
    REQUEST_RESOURCE("resource"),
    REQUEST_PROTOCOL_VERSION("protocol"),
    RESPONSE_CODE("code"),
    BODY_BYTES_SENT("bytes_size"),
    REFERER("referer"),
    USER_AGENT("agent");

    private final String value;

    LogFilterField(String value){
        this.value = value;
    }

    public static LogFilterField getFiled(String value) throws InvalidFilterValueException {
        for (LogFilterField filed: values()){
            if (filed.value.equalsIgnoreCase(value)){
                return filed;
            }
        }
        throw new InvalidFilterValueException("Invalid field value: " + value);
    }
}


