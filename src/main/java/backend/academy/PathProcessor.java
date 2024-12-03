package backend.academy;

import backend.academy.parser.FilePatternMatcher;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 * Класс для обработки путей и получения информации о файлах.
 */
@Log4j2
public class PathProcessor {
    List<Path> paths;

    public PathProcessor(String path) {
        paths = processPath(path);
    }

    /**
     * Обрабатывает путь и возвращает список файлов, соответствующих шаблону.
     *
     * @param path путь для обработки
     * @return список путей к файлам
     */
    private List<Path> processPath(String path) {
        try {
            return FilePatternMatcher.findMatchingFiles(path);
        } catch (Exception e) {
            log.error("Ошибка при обработке пути: {}", e.getMessage());
        }
        return List.of();
    }

    /**
     * Возвращает список имен файлов.
     *
     * @return список имен файлов
     */
    public List<String> getFileNames() {
        return paths.stream()
            .map(Path::getFileName)
            .map(Path::toString)
            .toList();
    }

    /**
     * Возвращает список путей к файлам в виде строк.
     *
     * @return список путей к файлам
     */
    public List<String> getFilePaths() {
        return paths.stream()
            .map(Path::toString)
            .toList();
    }
}

