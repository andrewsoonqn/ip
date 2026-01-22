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
        Arnold.printHorizontalLine();
        Arnold.printHi();
        Arnold.printHorizontalLine();
        Arnold.printBye();
        Arnold.printHorizontalLine();
        System.exit(0);
    }
}
