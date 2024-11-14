package backend.academy.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogFileReader extends Reader implements AutoCloseable {
    private BufferedReader bufferedReader;

    @Override
    public Stream<String> readLogs(String path) {
        try {
            bufferedReader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8));
            return bufferedReader.lines();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла " + path, e);
        }
    }

    public void close() throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

}
