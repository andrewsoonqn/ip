package arnold.messaging;

/**
 * Class for storing and sending messages to the user.
 */
public class Messenger {
    private String lastMessage = "";

    /**
     * Prints a message to the user and stores it.
     *
     * @param message The message to print.
     */
    public void printMessage(String message) {
        this.lastMessage = message;
    }

    /**
     * Retrieves the last message stored.
     *
     * @return The last message.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Greets the user.
     */
    public void hi() {
        this.lastMessage = "Hello! I'm Arnold" + "\n" + "What can I do for you?";
    }
}
