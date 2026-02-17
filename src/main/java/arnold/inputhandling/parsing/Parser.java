package arnold.inputhandling.parsing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.chatbotexceptions.NoSuchCommandException;
import arnold.inputhandling.Messages;
import arnold.inputhandling.strategies.InputHandlingStrategy;

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
     * Returns an unmodifiable view of the registered command-to-strategy map.
     *
     * @return The map of command names to their strategies.
     */
    public Map<String, InputHandlingStrategy> getRegisteredCommands() {
        return Collections.unmodifiableMap(strategies);
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
        String[] commandParts = splitCommandFromArguments(input);
        assert commandParts.length == 2 : "Expected two elements: command name and rest of the text";
        String commandName = commandParts[0];
        String rest = commandParts[1];

        InputHandlingStrategy strategy = strategies.get(commandName);
        if (strategy == null) {
            throw new NoSuchCommandException(Messages.noSuchCommand());
        }

        String[] expectedFlags = commandFlags.get(commandName);
        ParsedCommand command = parseFlags(" " + rest, expectedFlags);

        return new Result(strategy, command);
    }

    /**
     * Splits the input into command name and the rest of the text.
     * E.g., "deadline report /by 1/12/2026 2359" -> "deadline", "/by 1/12/2026 2359".
     *
     * @param input The full raw input string.
     * @return An array where index 0 is the command name and index 1 is the rest of the text.
     */
    private String[] splitCommandFromArguments(String input) {
        String[] inputParts = input.split("\\s+", 2);
        String commandName = inputParts[0];
        String rest = inputParts.length > 1 ? inputParts[1] : "";

        return new String[] {commandName, rest};
    }


    private ParsedCommand parseFlags(String text, String[] expectedFlags) {
        // Split text into description and flag segments
        // e.g., "meeting /from Mon 2pm /to Mon 4pm"
        // -> "meeting", "from Mon 2pm", "to Mon 4pm"
        String[] flagParts = text.split(FLAG_SIGNIFIER);

        String description = flagParts[0].strip();

        Map<String, String> flags = new HashMap<>();
        for (int i = 1; i < flagParts.length; i++) {
            String[] kv = flagParts[i].split("\\s+", 2);
            if (kv.length != 2) {
                continue;
            }
            String key = kv[0].strip();
            String value = kv[1].strip();
            if (flags.put(key, value) != null) {
                throw new ChatbotArgumentException(Messages.duplicateFlag(key));
            }
        }

        // Validates presence of required flags
        for (String flag : expectedFlags) {
            String key = flag.strip();
            String value = flags.get(key);
            if (value == null || value.isBlank()) {
                throw new ChatbotArgumentException(Messages.missingFlag(key));
            }
            assert flags.containsKey(key) : "Expected flag should be present after validation: " + key;
            assert !flags.get(key).isBlank() : "Expected flag should have non-blank value after validation: " + key;
        }

        return new ParsedCommand(description, flags);
    }

    /**
     * Holds the result of parsing: the resolved strategy and the parsed command data.
     */
    public record Result(InputHandlingStrategy strategy, ParsedCommand command) {
    }
}
