import Messaging.Messenger;
import Tasks.TaskList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Arnold bot = new Arnold(new Messenger(), new TaskList());

        // Scanner is used to get user input later
        Scanner scanner = new Scanner(System.in);

        bot.hi();
        bot.run(scanner);
    }
}
