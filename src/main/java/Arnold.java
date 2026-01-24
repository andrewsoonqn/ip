import java.util.Scanner;

import InputHandling.HiStrategy;
import InputHandling.InputProcessor;
import Messaging.Messenger;
import Tasks.Task;
import Tasks.TaskList;

public class Arnold {
    private final InputProcessor ip;

    public Arnold(Messenger msg, TaskList taskList) {
        this.ip = new InputProcessor(msg, taskList);
    }

    public void hi() {
        ip.processInput(new HiStrategy(), "");
    }

    public void run(Scanner scanner) {
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            ip.processInput(input);
        }
    }
}
