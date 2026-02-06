package arnold.messaging;

/**
 * Abstract class for sending messages to the user.
 */
public abstract class Messenger {
    /**
     * Prints text with indentation.
     *
     * @param text The text to print.
     */
    protected void indentPrint(String text) {
        System.out.print("    ");
        System.out.println(text);
    }

    /**
     * Prints a horizontal line.
     */
    protected void line() {
        indentPrint("_".repeat(60));
    }

    /**
     * Prints a message to the user.
     *
     * @param message The message to print.
     */
    public abstract void printMessage(String message);

    /**
     * Greets the user.
     */
    public abstract void hi();
}
