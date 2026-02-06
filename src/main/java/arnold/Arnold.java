package arnold;

import java.util.Scanner;

import arnold.events.EventBus;
import arnold.inputhandling.HiStrategy;
import arnold.inputhandling.InputProcessor;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

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
