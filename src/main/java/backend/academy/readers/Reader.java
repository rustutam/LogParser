package backend.academy.readers;

import java.util.stream.Stream;

public interface Reader {
    Stream<String> readLogs();
}
