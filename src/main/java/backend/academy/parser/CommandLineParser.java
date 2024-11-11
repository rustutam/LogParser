package backend.academy.parser;

import backend.academy.command_line.CommandLineArgs;
import backend.academy.config.AppConfig;
import backend.academy.filters.LogFilterField;
import backend.academy.model.Format;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class CommandLineParser {
    CommandLineArgs commandLineArgs;

    public CommandLineParser(CommandLineArgs commandLineArgs){
        this.commandLineArgs = commandLineArgs;
    }

    public AppConfig parseCommandLine(){
        try {
            String path = commandLineArgs.path();

            Optional<LocalDateTime> from = Optional.ofNullable(commandLineArgs.from());
            Optional<LocalDateTime> to = Optional.ofNullable(commandLineArgs.to());
            Optional<Format> format = Optional.ofNullable(commandLineArgs.format());

            Map<LogFilterField, Object> filter = Map.of(
                commandLineArgs.filterField(),
                commandLineArgs.filterValue()
            );
            return new AppConfig(path, from, to, format, filter);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Error parsing command line arguments", e);
        }
    }
}
