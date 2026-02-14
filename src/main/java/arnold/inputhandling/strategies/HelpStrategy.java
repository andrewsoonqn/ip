package arnold.inputhandling.strategies;

import java.util.Map;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.inputhandling.parsing.Parser;
import arnold.tasks.utils.TaskList;

/**
 * Strategy for handling the help command.
 * Iterates over all registered commands and displays their descriptions and usage examples.
 */
public class HelpStrategy implements InputHandlingStrategy {
    private final Parser parser;

    public HelpStrategy(Parser parser) {
        this.parser = parser;
    }

    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        Map<String, InputHandlingStrategy> commands = parser.getRegisteredCommands();
        StringBuilder sb = new StringBuilder("Available commands:\n");

        commands.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                String name = entry.getKey();
                InputHandlingStrategy strategy = entry.getValue();
                String description = strategy.getDescription();
                String example = strategy.getExampleUsage();

                sb.append(String.format("\n  %s", name));
                if (!description.isEmpty()) {
                    sb.append(String.format(" - %s", description));
                }
                if (!example.isEmpty()) {
                    sb.append(String.format("\n    Usage: %s", example));
                }
            });

        return CommandResult.success(sb.toString());
    }

    @Override
    public String getDescription() {
        return "Show all available commands";
    }

    @Override
    public String getExampleUsage() {
        return "help";
    }
}
