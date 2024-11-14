package backend.academy.command_line;

import backend.academy.command_line.converters.DataConverter;
import backend.academy.command_line.converters.FilterFieldConverter;
import backend.academy.command_line.converters.FormatConverter;
import backend.academy.filters.FilterField;
import backend.academy.model.Format;
import com.beust.jcommander.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CommandLineArgs {
    @Parameter(names = "--path", description = "Path to the log files or url", required = true)
    private String path;

    @Parameter(names = "--from", description = "Start date for filtering logs", converter = DataConverter.class)
    private LocalDateTime from;

    @Parameter(names = "--to", description = "End date for filtering logs", converter = DataConverter.class)
    private LocalDateTime to;

    @Parameter(names = "--format", description = "Output format", converter = FormatConverter.class)
    private Format format;

    @Parameter(names = "--filter", description = "Field and value to filter logs",
        converter = FilterFieldConverter.class, variableArity = true)
    private List<FilterField<?>> filterFieldList;
}
