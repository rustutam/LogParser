package backend.academy.parser;

import backend.academy.command_line.CommandLineArgs;
import backend.academy.config.AppConfig;
import backend.academy.filters.FilterField;
import backend.academy.model.Format;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Класс для парсинга аргументов командной строки и создания конфигурации приложения {@link AppConfig}.
 */
@Log4j2
@AllArgsConstructor
public class CommandLineParser {
    String[] args;

    /**
     * Парсит аргументы командной строки и создает объект {@link AppConfig}.
     *
     * @return объект {@link AppConfig}, содержащий параметры конфигурации
     * @throws ParameterException если аргументы командной строки некорректны
     */
    public AppConfig parseCommandLine() throws ParameterException {
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
            log.error(e.getMessage());
            throw e;
        }
    }


    /**
     * Создает и парсит объект {@link CommandLineArgs} из аргументов командной строки.
     *
     * @return объект {@link CommandLineArgs}, содержащий аргументы командной строки
     */
    private CommandLineArgs getCommandLineArgs() {
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder()
            .addObject(commandLineArgs)
            .build()
            .parse(args);

        return commandLineArgs;
    }
}
