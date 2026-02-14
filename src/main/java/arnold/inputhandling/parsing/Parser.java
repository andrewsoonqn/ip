package arnold.inputhandling.parsing;

import java.util.HashMap;
import java.util.Map;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.chatbotexceptions.NoSuchCommandException;
import arnold.inputhandling.InputHandlingStrategy;

// Solution below inspired by https://github.com/NUS-CS2103-AY2526-S2/ip/pull/445/

/**
 * The Parser class is responsible for interpreting raw user input to resolve a suitable handling
 * strategy and parse the command data including its description and associated flags.
 * It maintains a registry of commands, their corresponding strategies, and expected flags.
 */
public class Parser {
    private static final String FLAG_SIGNIFIER = " /";

    private final Map<String, InputHandlingStrategy> strategies = new HashMap<>();
    private final Map<String, String[]> commandFlags = new HashMap<>();

    /**
     * Registers a command with its strategy and expected flags.
     *
     * @param command The command name (e.g. "deadline").
     * @param strategy The strategy that handles this command.
     * @param expectedFlags The flags this command requires (e.g. "by", "from").
     */
    public void register(String command, InputHandlingStrategy strategy, String... expectedFlags) {
        strategies.put(command, strategy);
        commandFlags.put(command, expectedFlags);
    }

    /**
     * Parses raw user input into a resolved strategy and a ParsedCommand.
     *
     * @param input The full raw input string (e.g. "deadline report /by 1/12/2026 2359").
     * @return The result containing the resolved strategy and parsed command data.
     * @throws NoSuchCommandException If the command is not registered.
     * @throws ChatbotArgumentException If a required flag is missing or blank.
     */
    public Result parse(String input) {
        // Split input into command name and rest of the text
        // e.g., "deadline report /by 1/12/2026 2359" -> "deadline", "/by 1/12/2026 2359"
        String[] parts = input.split("\\s+", 2);
        String commandName = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        InputHandlingStrategy strategy = strategies.get(commandName);
        if (strategy == null) {
            throw new NoSuchCommandException("Sorry, I don't recognise that command!");
        }

        String[] expectedFlags = commandFlags.get(commandName);
        ParsedCommand command = parseFlags(" " + rest, expectedFlags);

        return new Result(strategy, command);
    }


    private ParsedCommand parseFlags(String text, String[] expectedFlags) {
        Map<String, String> flags = new HashMap<>();

        // Split text into description and flag portions
        // e.g., "meeting /from Mon 2pm /to Mon 4pm"
        // -> "meeting", "from Mon 2pm", "to Mon 4pm"
        String[] parts = text.split(FLAG_SIGNIFIER);

        String description = parts[0].strip();

        // Extracts flag key-value pairs from input
        for (int i = 1; i < parts.length; i++) {
            String[] keyValuePair = parts[i].split("\\s+", 2);
            if (keyValuePair.length == 2) {
                flags.put(keyValuePair[0].strip(), keyValuePair[1].strip());
            }
        }

        // Validates presence of required flags
        for (String flag : expectedFlags) {
            String key = flag.strip();
            String value = flags.get(key);
            if (value == null || value.isBlank()) {
                throw new ChatbotArgumentException(
                    String.format("Missing required flag: /%s", key));
            }
        }

        return new ParsedCommand(description, flags);
    }

    /**
     * Holds the result of parsing: the resolved strategy and the parsed command data.
     */
    public record Result(InputHandlingStrategy strategy, ParsedCommand command) {
        /**
         * Initializes a new Result.
         *
         * @param strategy The resolved strategy for this command.
         * @param command The parsed command data.
         */
        public Result {
        }

        /**
         * Returns the resolved strategy.
         *
         * @return The strategy to handle this command.
         */
        @Override
        public InputHandlingStrategy strategy() {
            return strategy;
        }

        /**
         * Returns the parsed command data.
         *
         * @return The parsed command containing description and flags.
         */
        @Override
        public ParsedCommand command() {
            return command;
        }
    }
}
