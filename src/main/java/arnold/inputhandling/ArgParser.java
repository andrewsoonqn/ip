package arnold.inputhandling;

import java.util.Map;

/**
 * Utility class for parsing user input arguments and flags.
 */
public class ArgParser {
    private static final String FLAG_SIGNIFIER = " /";

    /**
     * Splits the input text into command and arguments.
     *
     * @param text The input text to split.
     * @return An array containing the command and the arguments.
     */
    public static String[] getCommandArgs(String text) {
        String[] parts = text.split("\\s+", 2);

        // "Pair"
        return parts.length > 1 ? new String[] {parts[0], parts[1]} : new String[] {parts[0], ""};
    }

    /**
     * Parses flags from the input text.
     *
     * @param text The input text containing flags.
     * @return A map of flag names to flag values.
     */
    public static Map<String, String> getFlags(String text) {
        Map<String, String> flags = new java.util.HashMap<>();
        String[] parts = text.split(FLAG_SIGNIFIER);

        // The first part will be the task
        String taskDescription = parts[0].strip();
        flags.put("taskDescription", taskDescription);

        // The rest will be flags
        for (int i = 1; i < parts.length; i++) {
            String[] keyValue = parts[i].split("\\s+", 2);
            if (keyValue.length == 2) {
                flags.put(keyValue[0].strip(), keyValue[1].strip());
            }
        }
        return flags;
    }
}
