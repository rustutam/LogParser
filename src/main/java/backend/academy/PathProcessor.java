package backend.academy;

import backend.academy.parser.FilePatternMatcher;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

public class PathProcessor {
    PrintStream out;
    List<Path> paths;

    public PathProcessor(String path, PrintStream out) {
        this.out = out;
        paths = processPath(path);

    }

    private List<Path> processPath(String path) {
        try {
            return FilePatternMatcher.findMatchingFiles(path);
        } catch (Exception e) {
            out.println("Ошибка при обработке пути: " + e.getMessage());
        }
        return List.of();
    }

    public List<String> getFileNames() {
        return paths.stream().map(Path::getFileName).map(Path::toString).toList();
    }

    public List<String> getFilePaths() {
        return paths.stream().map(Path::toString).toList();
    }
}

