import java.util.Scanner;

public class Arnold {
    // ---------------------------------------------
    // Helper methods
    // ---------------------------------------------
    private static void indentPrint(String text) {
        System.out.print("    ");
        System.out.println(text);
    }

    private static void line() {
        indentPrint("____________________________________________________________");
    }

    public static void printMessage(String text) {
        line();
        for (String line : text.split("\n")) {
            indentPrint(line);
        }
        line();
    }

    // ---------------------------------------------
    // Chatbot commands
    // ---------------------------------------------

    private static void hi() {
        String message = "Hello! I'm Arnold" + "\n" + "What can I do for you?";
        printMessage(message);
    }

    private static void bye() {
        printMessage("Bye. Hope to see you again soon!");
    }

    }

    public static void main(String[] args) {
        // Scanner is used to get user input later
        Scanner scanner = new Scanner(System.in);

        hi();

        while (true) {
            String input = scanner.nextLine();
            String inputCommand = input.strip().toLowerCase();
            if (inputCommand.equals("bye")) {
                bye();
                break;
            } else {
                Arnold.line();
                indentPrint(input);
                Arnold.line();
            }
        }

    }
}
