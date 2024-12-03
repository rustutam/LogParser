package backend.academy.parser;

import backend.academy.config.AppConfig;
import backend.academy.model.Format;
import com.beust.jcommander.ParameterException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandLineParserTest {

    @Test
    void parseCommandLine_validArgs() {
        // Arrange

        String[] args =
            {"--path", "logs/log.txt", "--from", "2023-01-01", "--to", "2023-12-31", "--format", "markdown", "--filter",
                "code:200", "--filter", "ip:192.168.0.0"};
        CommandLineParser parser = new CommandLineParser(args);

        String path = "logs/log.txt";
        LocalDateTime from = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2023, 12, 31, 0, 0);
        Format format = Format.MD;
        int filtersCount = 2;
        // Act
        AppConfig config = parser.parseCommandLine();

        // Assert
        assertEquals(path, config.inputFilePath());
        assertEquals(from, config.startDate());
        assertEquals(to, config.endDate());
        assertEquals(format, config.format());
        assertEquals(filtersCount, config.filterFieldList().size());

    }

    @Test
    void parseCommandLine_invalidDateRange() {
        // Arrange
        String[] args = {"--path", "logs/", "--from", "2023-12-31", "--to", "2023-01-01"};
        CommandLineParser parser = new CommandLineParser(args);

        // Act & Assert
        assertThrows(ParameterException.class, parser::parseCommandLine);
    }

    @Test
    void parseCommandLine_missingPath() {
        // Arrange
        String[] args = {"--from", "2023-01-01T00:00", "--to", "2023-12-31T23:59"};
        CommandLineParser parser = new CommandLineParser(args);

        // Act & Assert
        assertThrows(ParameterException.class, parser::parseCommandLine);
    }

    @Test
    void parseCommandLine_defaultValues() {
        // Arrange
        String[] args = {"--path", "logs/"};
        CommandLineParser parser = new CommandLineParser(args);

        // Act
        AppConfig config = parser.parseCommandLine();

        // Assert
        assertEquals("logs/", config.inputFilePath());
        assertEquals(LocalDateTime.MIN, config.startDate());
        assertEquals(LocalDateTime.MAX, config.endDate());
        assertEquals(Format.MD, config.format());
        assertTrue(config.filterFieldList().isEmpty());
    }
}
