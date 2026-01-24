package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

public class MarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        taskList.markTask(Integer.parseInt(input));
        msg.printMessage(
                "Nice! I've marked this task as done:\n"
                + taskList.listTasks());
    }
}
