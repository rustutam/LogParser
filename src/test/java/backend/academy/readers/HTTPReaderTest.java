package backend.academy.readers;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPReaderTest {

    @Test
    void readLogs_invalidUrl() {
        // Arrange
        HTTPReader httpReader = new HTTPReader();
        String url = "invalid-url";

        // Act
        Stream<String> result = httpReader.readLogs(url);

        // Assert
        assertEquals(0, result.count());
    }
}
