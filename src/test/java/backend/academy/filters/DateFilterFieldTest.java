package backend.academy.filters;

import backend.academy.exceptions.InvalidFilterValueException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateFilterFieldTest {
    private DateFilterField dateFilterField;

    @BeforeEach
    void setUp() {
        dateFilterField = new DateFilterField();
    }

    @Test
    void parseValidValue() {
        // Arrange
        String validDateString = "2023-10-15";
        LocalDateTime validDate = LocalDateTime.of(2023, 10, 15, 0, 0);

        // Act
        dateFilterField.parseValue(validDateString);

        // Assert
        assertEquals(validDate, dateFilterField.value());
    }

    @Test
    void parseInvalidValue() {
        // Arrange
        String invalidDateString = "223232323";

        // Act
        InvalidFilterValueException exception = assertThrows(InvalidFilterValueException.class, () -> {
            dateFilterField.parseValue(invalidDateString);
        });

        // Assert
        assertInstanceOf(DateTimeParseException.class, exception.getCause());
    }
}
