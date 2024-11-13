package backend.academy.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogFileReader implements Reader {
    private final List<String> filePaths;

    public LogFileReader(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    public Stream<String> readLogs() {
        return filePaths.stream()
            .flatMap(filePath -> {
                try {

                    BufferedReader br = new BufferedReader(new FileReader(filePath));
                    return br.lines();
                } catch (IOException e) {
                    log.error("Ошибка в {}", filePath, e);
                    return Stream.empty();
                }

            });

    }

}
