import java.util.Scanner;

public class Arnold {
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    private static void hi() {
        System.out.println("Hello! I'm Arnold");
        System.out.println("What can I do for you?");
    }

    private static void bye() {
        System.out.println("Bye. Hope to see you again soon!");
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
                System.out.println(input);
                Arnold.line();
            }
        }

    }
}
