package arnold;

import java.util.Scanner;

import arnold.events.EventBus;
import arnold.inputhandling.HiStrategy;
import arnold.inputhandling.InputProcessor;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Main chatbot class.
 */
public class Arnold {
    private final InputProcessor inputProcessor;
    private boolean isRunning = true;

    /**
     * Initializes a new instance of the Arnold chatbot.
     *
     * @param msg The messenger to use for communication.
     * @param taskList The task list to manage.
     */
    public Arnold(Messenger msg, TaskList taskList) {
        this.inputProcessor = new InputProcessor(msg, taskList);
    }

    /**
     * Greets the user.
     */
    public void hi() {
        inputProcessor.processInput(new HiStrategy(), "");
    }

    /**
     * Runs the chatbot and processes user input.
     *
     * @param scanner The scanner to read input from.
     */
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
