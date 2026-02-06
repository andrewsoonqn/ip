package arnold.chatbotexceptions;

/**
 * Exception thrown when an unknown command is encountered.
 */
public class NoSuchCommandException extends ChatbotException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
