package backend.academy.parser;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс для поиска файлов, соответствующих заданному GLOB-шаблону.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilePatternMatcher {

    /**
     * Ищет файлы, соответствующие заданному GLOB-шаблону.
     *
     * @param globPattern GLOB-шаблон для поиска файлов
     * @return список путей к файлам, соответствующим шаблону
     * @throws IOException если произошла ошибка при поиске файлов
     */
    public static List<Path> findMatchingFiles(String globPattern) throws IOException {
        // Создаем PathMatcher для обработки GLOB-выражения
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + globPattern);

        // Определяем начальную директорию
        Path startPath = Paths.get("").toAbsolutePath(); // Используем текущую директорию по умолчанию

        List<Path> matchedFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(startPath)) {
            paths
                .filter(Files::isRegularFile)  // Ищем только файлы, исключая директории
                .filter(matcher::matches)      // Фильтруем по GLOB-шаблону
                .forEach(matchedFiles::add);   // Добавляем подходящие файлы в список
        } catch (IOException e) {
            throw new IOException("Ошибка при поиске файлов: " + e.getMessage(), e);
        }

        return matchedFiles;
    }
}

