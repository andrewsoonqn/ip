package arnold.tasks;

import arnold.tasks.utils.TaskType;

/**
 * Represents a todo task.
 */
public class Todo extends Task {
    /**
     * Initializes a new instance of a todo task.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.TODO;
    }
}
