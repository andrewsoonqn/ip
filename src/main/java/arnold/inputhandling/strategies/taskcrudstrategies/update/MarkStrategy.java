package arnold.inputhandling.strategies.taskcrudstrategies.update;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.strategies.taskcrudstrategies.TaskIdStrategy;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

/**
 * Strategy for handling the mark command.
 */
public class MarkStrategy extends TaskIdStrategy {
    /**
     * Marks the specified task as done.
     *
     * @param taskId The ID of the task to mark.
     * @param taskList The task list containing the task.
     * @return The response message confirming the task was marked.
     */
    @Override
    protected CommandResult execute(int taskId, TaskList taskList) {
        Task task = taskList.markTask(taskId);
        return CommandResult.success(Messages.taskMarked(TaskString.withoutIndex(task)));
    }

    @Override
    public String getDescription() {
        return "Mark a task as done";
    }

    @Override
    public String getExampleUsage() {
        return "mark 1";
    }
}
