package backend.academy.readers;

import lombok.extern.log4j.Log4j2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

@Log4j2
public class LogFileReader implements Reader {
    private final String filePath;

    public LogFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<String> readLogs() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            return br.lines();
        } catch (IOException e) {
            log.error("Ошибка в {}", e.getClass());
        }
        return Stream.empty();

    }

}
