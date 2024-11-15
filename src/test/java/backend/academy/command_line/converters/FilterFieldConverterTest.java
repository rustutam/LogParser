package backend.academy.command_line.converters;

import backend.academy.exceptions.InvalidFilterFieldException;
import backend.academy.exceptions.InvalidFilterValueException;
import backend.academy.filters.AgentFilterField;
import backend.academy.filters.BytesFilterField;
import backend.academy.filters.DateFilterField;
import backend.academy.filters.FilterField;
import backend.academy.filters.IpFilterField;
import backend.academy.filters.MethodFilterField;
import backend.academy.filters.ProtocolFilterField;
import backend.academy.filters.RefererFilterField;
import backend.academy.filters.ResourceFilterField;
import backend.academy.filters.ResponseCodeFilterField;
import backend.academy.filters.UserFilterField;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterFieldConverterTest {
    private FilterFieldConverter filterFieldConverter;
    @BeforeEach
    void setUp() {
        filterFieldConverter = new FilterFieldConverter("--filter");
    }

    @Test
    void convertWithIncorrectFormat() {
        // Arrange
        String incorrectValue = "test";

        // Act
        ParameterException exception = assertThrows(ParameterException.class, () -> {
            filterFieldConverter.convert(incorrectValue);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Фильтр должен быть в формате field:value"));
    }

    @Test
    void convertWithInvalidFilterField() {
        // Arrange
        String incorrectValue = "test:value";

        // Act
        ParameterException exception = assertThrows(ParameterException.class, () -> {
            filterFieldConverter.convert(incorrectValue);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Неверное значение для --filter: test"));
        assertInstanceOf(InvalidFilterFieldException.class, exception.getCause());
    }

    @Test
    void convertWithInvalidFilterValue() {
        // Arrange
        String incorrectValue = "code:invalid-value";

        // Act
        ParameterException exception = assertThrows(ParameterException.class, () -> {
            filterFieldConverter.convert(incorrectValue);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Неверное значение поля фильтрации code: invalid-value"));
        assertInstanceOf(InvalidFilterValueException.class, exception.getCause());
    }

    @Test
    void convertIpField() {
        // Arrange
        String correctValue = "ip:192.168.0.0";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(IpFilterField.class, filterField);
    }

    @Test
    void convertUserField() {
        // Arrange
        String correctValue = "user:User";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(UserFilterField.class, filterField);
    }

    @Test
    void convertDateField() {
        // Arrange
        String correctValue = "date:2012-02-02";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(DateFilterField.class, filterField);
    }

    @Test
    void convertMethodField() {
        // Arrange
        String correctValue = "method:GET";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(MethodFilterField.class, filterField);
    }

    @Test
    void convertResourceField() {
        // Arrange
        String correctValue = "resource:github.com";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(ResourceFilterField.class, filterField);
    }

    @Test
    void convertProtocolField() {
        // Arrange
        String correctValue = "protocol:http";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(ProtocolFilterField.class, filterField);
    }



    @Test
    void convertWithCorrectData() {
        // Arrange
        String correctValue = "code:200";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(ResponseCodeFilterField.class, filterField);

    }

    @Test
    void convertByteField() {
        // Arrange
        String correctValue = "bytes_size:1234";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(BytesFilterField.class, filterField);
    }

    @Test
    void convertRefererField() {
        // Arrange
        String correctValue = "referer:google.com";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(RefererFilterField.class, filterField);
    }

    @Test
    void convertAgentField() {
        // Arrange
        String correctValue = "agent:Mozilla";

        // Act
        FilterField<?> filterField = filterFieldConverter.convert(correctValue);

        // Assert
        assertInstanceOf(AgentFilterField.class, filterField);
    }
}
