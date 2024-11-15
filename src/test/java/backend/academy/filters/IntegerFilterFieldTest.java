package backend.academy.filters;

import backend.academy.exceptions.InvalidFilterValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntegerFilterFieldTest {
    private IntegerFilterField integerFilterField;

    @BeforeEach
    void setUp() {
        integerFilterField = new IntegerFilterField();
    }

    @Test
    void parseValidValue() {
        // Arrange
        String validDateString = "34";
        int validValue = 34;

        // Act
        integerFilterField.parseValue(validDateString);

        // Assert
        assertEquals(validValue, integerFilterField.value());
    }

    @Test
    void parseInvalidValue() {
        // Arrange
        String invalidValueString = "two";

        // Act
        InvalidFilterValueException exception = assertThrows(InvalidFilterValueException.class, () -> {
            integerFilterField.parseValue(invalidValueString);
        });

        // Assert
        assertInstanceOf(NumberFormatException.class, exception.getCause());
    }
}
