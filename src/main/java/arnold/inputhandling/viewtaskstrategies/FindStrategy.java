package arnold.inputhandling.viewtaskstrategies;

import java.util.function.Predicate;

import arnold.inputhandling.InputHandlingStrategy;
import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the list command.
 */
public class FindStrategy implements InputHandlingStrategy {
    /**
     * Handles the find command by searching for tasks that contain the input string.
     *
     * @param input The keyword to search for.
     * @param msg The messenger to use for communication.
     * @param taskList The task list to search within.
     */
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
