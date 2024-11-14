package backend.academy.parser;

import backend.academy.command_line.CommandLineArgs;
import backend.academy.config.AppConfig;
import backend.academy.filters.FilterField;
import backend.academy.model.Format;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandLineParser {
    String[] args;

    public CommandLineParser(String[] args) {
        this.args = args;
    }

    public AppConfig parseCommandLine(PrintStream out) {
        try {
            CommandLineArgs commandLineArgs = getCommandLineArgs();

            String path = commandLineArgs.path();
            LocalDateTime from = Optional.ofNullable(commandLineArgs.from()).orElse(LocalDateTime.MIN);
            LocalDateTime to = Optional.ofNullable(commandLineArgs.to()).orElse(LocalDateTime.MAX);
            if (from.isAfter(to)) {
                throw new ParameterException("Дата начала должна быть меньше даты окончания");
            }
            Format format = Optional.ofNullable(commandLineArgs.format()).orElse(Format.MD);
            List<FilterField<?>> filterFields =
                Optional.ofNullable(commandLineArgs.filterFieldList()).orElse(List.of());
            return new AppConfig(path, from, to, format, filterFields);
        } catch (ParameterException e) {
            out.println(e.getMessage());
        }
        return null;
    }

    private CommandLineArgs getCommandLineArgs() {
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder()
            .addObject(commandLineArgs)
            .build()
            .parse(args);

        return commandLineArgs;
    }
}
