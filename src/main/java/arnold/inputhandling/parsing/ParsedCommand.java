package arnold.inputhandling.parsing;

import java.util.Map;

/**
 * Represents a parsed user command with its description and flags.
 */
public class ParsedCommand {
    private final String description;
    private final Map<String, String> flags;

    /**
     * Initializes a new ParsedCommand.
     *
     * @param description The main description/argument of the command.
     * @param flags The parsed flags and their values.
     */
    public ParsedCommand(String description, Map<String, String> flags) {
        this.description = description;
        this.flags = flags;
    }

    /**
     * Returns the main description/argument of the command.
     *
     * @return The description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the value of a specific flag.
     *
     * @param name The flag name (without the leading slash).
     * @return The flag value, or null if the flag was not provided.
     */
    public String getFlag(String name) {
        return flags.get(name);
    }
}
