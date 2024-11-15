package backend.academy.model;

import backend.academy.exceptions.InvalidFilterFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogFilterFieldTest {

    @ParameterizedTest
    @CsvSource({
        "ip, IP",
        "user, USER",
        "date, DATE",
        "method, REQUEST_METHOD",
        "resource, REQUEST_RESOURCE",
        "protocol, REQUEST_PROTOCOL_VERSION",
        "code, RESPONSE_CODE",
        "bytes_size, BODY_BYTES_SENT",
        "referer, REFERER",
        "agent, USER_AGENT"
    })
    void getField(String input, LogFilterField expected) {
        // Arrange & Act
        LogFilterField filterField = LogFilterField.getField(input);

        // Assert
        assertEquals(expected, filterField);
    }

    @Test
    void getFieldInvalidField() {
        // Arrange
        String invalidField = "invalid";

        // Act
        InvalidFilterFieldException exception = assertThrows(InvalidFilterFieldException.class, () -> {
            LogFilterField.getField(invalidField);
        });

        // Assert
        assertEquals("Invalid field value: " + invalidField, exception.getMessage());

    }
}
