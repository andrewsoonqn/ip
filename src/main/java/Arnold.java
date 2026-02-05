import events.EventBus;
import inputhandling.HiStrategy;
import inputhandling.InputProcessor;
import Messaging.Messenger;
import Tasks.TaskList;

import java.util.Scanner;

public class Arnold {
    private final InputProcessor inputProcessor;
    private boolean isRunning = true;

    public Arnold(Messenger msg, TaskList taskList) {
        this.inputProcessor = new InputProcessor(msg, taskList);
    }

    public void hi() {
        inputProcessor.processInput(new HiStrategy(), "");
    }

    public void run(Scanner scanner) {
        // Exit command will trigger shutdown
        EventBus.getInstance().registerShutdownHandler(() -> isRunning = false);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            inputProcessor.processInput(input);
            if (!isRunning) {
                break;
            }
        }
    }
}
