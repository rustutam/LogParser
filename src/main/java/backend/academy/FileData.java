package backend.academy;

import backend.academy.readers.Reader;
import java.util.List;

public record FileData(List<String> fileNames, Reader reader, List<String> filePaths) {
}
