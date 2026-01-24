import java.util.Scanner;

import Events.EventBus;
import InputHandling.HiStrategy;
import InputHandling.InputProcessor;
import Messaging.Messenger;
import Tasks.TaskList;

public class Arnold {
    private final InputProcessor ip;
    private boolean running = true;

    public Arnold(Messenger msg, TaskList taskList) {
        this.ip = new InputProcessor(msg, taskList);
    }

    public void hi() {
        ip.processInput(new HiStrategy(), "");
    }

    public void run(Scanner scanner) {
        // Exit command will trigger shutdown
        EventBus.getInstance().registerShutdownHandler(() -> running = false);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            ip.processInput(input);
            if (!running) {
                break;
            }
        }
    }
}
