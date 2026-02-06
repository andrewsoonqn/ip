package arnold.messaging;

/**
 * Concrete implementation of Messenger that prints to the console.
 */
public class DefaultMessenger extends Messenger {
    @Override
    public void printMessage(String text) {
        line();
        for (String line : text.split("\n")) {
            indentPrint(line);
        }
        line();
    }

    /**
     * Greets the user.
     */
    @Override
    public void hi() {
        String message = "Hello! I'm Arnold" + "\n" + "What can I do for you?";
        printMessage(message);
    }
}
