package backend.academy;

import backend.academy.config.AppConfig;
import backend.academy.parser.CommandLineParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        try {
            CommandLineParser commandLineParser = new CommandLineParser(args);
            AppConfig appConfig = commandLineParser.parseCommandLine();
            App app = new App(appConfig);
            app.run();
        } catch (Exception e) {
            System.exit(1);
        }
    }

}
