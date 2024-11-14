package backend.academy.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogFileReader extends Reader {
    @Override
    public Stream<String> readLogs(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            return br.lines();
        } catch (IOException e) {
            log.error("Ошибка в {}", e.getClass());
        }
        return Stream.empty();
    }

}
