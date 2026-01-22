import java.util.Scanner;

public class Arnold {
    private static void printHorizontalLine() {
        System.out.println("____________________________________________________________");
    }

    private static void printHi() {
        System.out.println("Hello! I'm Arnold");
        System.out.println("What can I do for you?");
    }

    private static void printBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        // Scanner is used to get user input later
        Scanner scanner = new Scanner(System.in);

        Arnold.printHorizontalLine();
        Arnold.printHi();
        Arnold.printHorizontalLine();

        while (true) {
            String input = scanner.nextLine();
            String inputCommand = input.strip().toLowerCase();
            if (inputCommand.equals("bye")) {
                Arnold.printHorizontalLine();
                Arnold.printBye();
                Arnold.printHorizontalLine();
                System.exit(0);
            } else {
                Arnold.printHorizontalLine();
                System.out.println(input);
                Arnold.printHorizontalLine();
            }
        }

    }
}
