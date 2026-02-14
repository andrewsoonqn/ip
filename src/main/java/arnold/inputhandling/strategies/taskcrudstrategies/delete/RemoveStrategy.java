package arnold.inputhandling.strategies.taskcrudstrategies.delete;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.strategies.taskcrudstrategies.TaskIdStrategy;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

/**
 * Strategy for handling the remove command.
 */
public class RemoveStrategy extends TaskIdStrategy {
    /**
     * Removes the specified task from the task list.
     *
     * @param taskId The ID of the task to remove.
     * @param taskList The task list to remove the task from.
     * @return The response message confirming the task was removed.
     */
    @Override
    protected CommandResult execute(int taskId, TaskList taskList) {
        Task task = taskList.removeTask(taskId);
        return CommandResult.success(
            Messages.taskRemoved(TaskString.withoutIndex(task), taskList.getSize()));
    }

    @Override
    public String getDescription() {
        return "Delete a task";
    }

    @Override
    public String getExampleUsage() {
        return "delete 1";
    }
}
