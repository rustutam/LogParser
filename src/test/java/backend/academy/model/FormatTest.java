package backend.academy.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatTest {

    @ParameterizedTest
    @CsvSource({
        "markdown, MD",
        "adoc, ADOC"
    })
    void getFormat(String input, Format expected) {
        // Arrange & Act
        Format filterField = Format.getFormat(input);

        // Assert
        assertEquals(expected, filterField);
    }

    @Test
    void getInvalidFormat() {
        // Arrange
        String invalidField = "invalid";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Format.getFormat(invalidField);
        });

        // Assert
        assertEquals("Invalid format value: " + invalidField, exception.getMessage());

    }
}

