package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the greeting command.
 */
public class HiStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.hi();
    }
}
