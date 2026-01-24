package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

public class UnmarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        taskList.unmarkTask(Integer.parseInt(input));
        msg.printMessage(
                "OK, I've marked this task as not done yet:\n"
                + taskList.listTasks());
    }
}
