package backend.academy.command_line;

import backend.academy.command_line.converters.DataConverter;
import backend.academy.command_line.converters.FormatConverter;
import backend.academy.command_line.converters.LogFilterFieldConverter;
import backend.academy.filters.LogFilterField;
import backend.academy.model.Format;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter public class CommandLineArgs {
    @Parameter(names = "--path", description = "Path to the log files or url", required = true)
    private String path;

    @Parameter(names = "--from", description = "Start date for filtering logs",converter = DataConverter.class)
    private LocalDateTime from;

    @Parameter(names = "--to", description = "End date for filtering logs",converter = DataConverter.class)
    private LocalDateTime to;

    @Parameter(names = "--format", description = "Output format", converter = FormatConverter.class)
    private Format format;

    @Parameter(names = "--filter-field", description = "Field to filter logs",converter = LogFilterFieldConverter.class)
    private LogFilterField filterField;

    @Parameter(names = "--filter-value", description = "Value to filter logs")
    private String filterValue;

}
