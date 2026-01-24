package Messaging;

public class Messenger {
    private void indentPrint(String text) {
        System.out.print("    ");
        System.out.println(text);
    }

    private void line() {
        indentPrint("_".repeat(60));
    }

    public void printMessage(String text) {
        line();
        for (String line : text.split("\n")) {
            indentPrint(line);
        }
        line();
    }

    public void hi() {
        String message = "Hello! I'm Arnold" + "\n" + "What can I do for you?";
        printMessage(message);
    }
}
