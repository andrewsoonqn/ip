package arnold.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonCreator
    public Todo(@JsonProperty("description") String description) {
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
