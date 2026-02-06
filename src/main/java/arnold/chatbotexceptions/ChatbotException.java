package arnold.chatbotexceptions;

/**
 * Generic exception thrown by the chatbot.
 */
public class ChatbotException extends RuntimeException {
    public ChatbotException(String message) {
        super(message);
    }
}
