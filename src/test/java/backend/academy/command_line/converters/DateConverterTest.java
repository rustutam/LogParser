package backend.academy.command_line.converters;

import com.beust.jcommander.ParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateConverterTest {
    @Test
    void convertWithCorrectData() {
        //Arrange
        String optionName = "--data";
        DateConverter dateConverter = new DateConverter(optionName);
        String date = "2021-05-04";
        LocalDateTime correctDate = LocalDateTime.of(2021, 5, 4, 0, 0);

        //Act
        LocalDateTime convertedDate = dateConverter.convert(date);

        //Assert
        assertEquals(correctDate, convertedDate);
    }

    @Test
    void convertWithIncorrectData() {
        // Arrange
        String optionName = "--data";
        DateConverter dateConverter = new DateConverter(optionName);
        String invalidDate = "invalid-date";

        // Act
        ParameterException exception = assertThrows(ParameterException.class, () -> {
            dateConverter.convert(invalidDate);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Неверное значение для " + optionName));
        assertInstanceOf(DateTimeParseException.class, exception.getCause());
    }
}

