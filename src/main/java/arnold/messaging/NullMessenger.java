package arnold.messaging;

/**
 * Messenger implementation that does nothing.
 * Used for silent operations like data loading where
 * a {@link Messenger} is required as a dependency
 * but no output is desired.
 */
public class NullMessenger extends Messenger {
    /**
     * Prints a message to the user.
     *
     * @param message The message to print.
     */
    @Override
    public void printMessage(String message) {
    }

    /**
     * Greets the user.
     */
    @Override
    public void hi() {
    }
}
