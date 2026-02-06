package arnold.inputhandling;

import arnold.events.EventBus;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the exit command.
 */
public class ExitStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage("Bye. Hope to see you again soon!");

        // Bot will receive and shut down
        EventBus.getInstance().publishShutdown();
    }
}
