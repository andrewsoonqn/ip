package arnold.chatbotexceptions;

/**
 * Exception thrown when a storage operation (save or load) fails.
 */
public class StorageException extends ChatbotException {
    /**
     * Creates a StorageException with the specified message.
     *
     * @param message The error message.
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Creates a StorageException with the specified message and cause.
     *
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
