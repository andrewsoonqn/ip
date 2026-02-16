package arnold.inputhandling.strategies.taskcrudstrategies.update;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.strategies.taskcrudstrategies.TaskIdStrategy;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

/**
 * Strategy for handling the unmark command.
 */
public class UnmarkStrategy extends TaskIdStrategy {
    /**
     * Marks the specified task as not done.
     *
     * @param taskId The ID of the task to unmark.
     * @param taskList The task list containing the task.
     * @return The response message confirming the task was unmarked.
     */
    @Override
    protected CommandResult execute(int taskId, TaskList taskList) {
        Task task = taskList.unmarkTask(taskId);
        return CommandResult.success(Messages.taskUnmarked(TaskString.withoutIndex(task)));
    }

    @Override
    public String getDescription() {
        return "Mark a task as not done";
    }

    @Override
    public String getExampleUsage() {
        return "unmark 1";
    }
}
