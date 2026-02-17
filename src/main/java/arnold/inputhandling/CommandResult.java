package arnold.inputhandling;

/**
 * Represents the result of executing a command, encapsulating a message, error status,
 * and whether the app should terminate.
 * <p>
 * This class provides factory methods to create instances that represent a successful
 * result, an error result, or a signal to terminate the application.
 */
public class CommandResult {
    private final String message;
    private final boolean isError;
    private final boolean shouldExit;

    private CommandResult(String message, boolean isError, boolean shouldExit) {
        this.message = message;
        this.isError = isError;
        this.shouldExit = shouldExit;
    }

    // Factory methods
    public static CommandResult success(String message) {
        return new CommandResult(message, false, false);
    }

    public static CommandResult error(String message) {
        return new CommandResult(message, true, false);
    }

    public static CommandResult exit(String message) {
        return new CommandResult(message, false, true);
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return isError;
    }

    public boolean shouldExit() {
        return shouldExit;
    }
}
