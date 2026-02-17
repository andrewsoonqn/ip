package arnold.inputhandling;

/**
 * A utility class for attaching example usage text to messages.
 * This decouples example usage from the error messages themselves,
 * allowing the caller to decide whether to attach example usage.
 * This class is designed to be non-instantiable and operates entirely through static methods.
 */
public final class ExampleUsage {
    private ExampleUsage() {
    }

    /**
     * Appends an example usage string to the given message.
     *
     * @param message The original message to attach the example usage to.
     * @param exampleUsage The example usage string to append.
     * @return The message with the example usage appended on a new line.
     */
    public static String attach(String message, String exampleUsage) {
        return String.format("%s\nExample usage: %s", message, exampleUsage);
    }
}
