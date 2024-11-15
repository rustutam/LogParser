package backend.academy.command_line.converters;

import backend.academy.model.Format;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatConverterTest {
    private FormatConverter formatConverter;

    @BeforeEach
    void setUp() {
        formatConverter = new FormatConverter("--format");
    }

    @Test
    void convertWithValidValue() {
        // Arrange
        String validFormat = "markdown";

        // Act
        Format converted = formatConverter.convert(validFormat);

        // Assert
        assertEquals(Format.MD, converted);

    }

    @Test
    void convertWithInvalidValue() {
        // Arrange
        String invalidFormat = "txt";

        // Act
        ParameterException exception = assertThrows(ParameterException.class, () -> {
            formatConverter.convert(invalidFormat);
        });

        // Assert
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());

    }
}
