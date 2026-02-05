package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

public class ListStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage(
                "Here are the tasks in your list:\n"
                        + taskList);
    }
}
