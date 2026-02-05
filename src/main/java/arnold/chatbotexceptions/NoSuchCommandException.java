package arnold.chatbotexceptions;

public class NoSuchCommandException extends ChatbotException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
