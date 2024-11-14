package backend.academy.readers;

import java.util.List;
import java.util.stream.Stream;

public abstract class Reader {
    public abstract Stream<String> readLogs(String path);

    public Stream<String> readLogs(List<String> paths) {
        return paths.stream()
            .flatMap(this::readLogs);
    }
}
