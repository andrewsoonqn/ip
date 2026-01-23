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

    // ---------------------------------------------
    // Chatbot commands
    // ---------------------------------------------
    private static void hi() {
        indentPrint("Hello! I'm Arnold");
        indentPrint("What can I do for you?");
    }

    private static void bye() {
        indentPrint("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        // Scanner is used to get user input later
        Scanner scanner = new Scanner(System.in);

        Arnold.line();
        Arnold.hi();
        Arnold.line();

        while (true) {
            String input = scanner.nextLine();
            String inputCommand = input.strip().toLowerCase();
            if (inputCommand.equals("bye")) {
                Arnold.line();
                Arnold.bye();
                Arnold.line();
                System.exit(0);
            } else {
                Arnold.line();
                indentPrint(input);
                Arnold.line();
            }
        }

    }
}
