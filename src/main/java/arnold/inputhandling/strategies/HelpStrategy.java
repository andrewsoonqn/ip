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
            .forEach(entry -> sb.append(formatCommandEntry(entry.getKey(), entry.getValue())));

        return CommandResult.success(sb.toString());
    }

    /**
     * Formats a single command entry with its name, description, and example usage.
     *
     * @param name The command name
     * @param strategy The strategy containing description and usage information
     * @return A formatted string representing the command entry
     */
    private String formatCommandEntry(String name, InputHandlingStrategy strategy) {
        StringBuilder entryBuilder = new StringBuilder();
        String description = strategy.getDescription();
        String example = strategy.getExampleUsage();

        entryBuilder.append(String.format("\n * %s", name));
        if (!description.isEmpty()) {
            entryBuilder.append(String.format("\n    %s", description));
        }
        if (!example.isEmpty()) {
            entryBuilder.append(String.format("\n    `%s`", example));
        }

        return entryBuilder.toString();
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
