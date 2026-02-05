package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

public class MarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId = Integer.parseInt(input);
        Task task = taskList.markTask(taskId);
        msg.printMessage(
                "Nice! I've marked this task as done:\n"
                        + TaskString.withoutIndex(task));
    }
}
