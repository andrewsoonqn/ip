package arnold.inputhandling;

import java.util.function.Predicate;

import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the list command.
 */
public class FindStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        Predicate<Task> predicate = task -> task
                .getDescription()
                .toLowerCase()
                .contains(input.toLowerCase());

        msg.printMessage(
                "Here are the matching tasks in your list:\n"
                        + TaskString.listWithIndex(
                        taskList.findTasks(predicate)));
    }
}
