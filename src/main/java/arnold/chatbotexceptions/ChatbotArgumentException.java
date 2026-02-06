package arnold.chatbotexceptions;

/**
 * Represents an exception thrown when a command argument is invalid.
 * For example, out-of-range values, non-existent options, or missing required arguments.
 * For invalid (non-existent) commands, use {@link NoSuchCommandException}.
 */
public class ChatbotArgumentException extends ChatbotException {
    public ChatbotArgumentException(String message) {
        super(message);
    }
}
