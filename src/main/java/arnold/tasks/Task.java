package arnold.tasks;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import arnold.tasks.utils.TaskType;

/**
 * Represents a generic task.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Todo.class, name = "todo"),
    @JsonSubTypes.Type(value = Deadline.class, name = "deadline"),
    @JsonSubTypes.Type(value = Event.class, name = "event")
})
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class Task {
    @JsonProperty("description")
    private final String description;

    @JsonProperty("isDone")
    private boolean isDone;

    /**
     * Initializes a new instance of a task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Unmarks the task as done.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns the type of the task.
     *
     * @return The task type.
     */
    public abstract TaskType getTaskType();

    public boolean isDone() {
        return isDone;
    }
}
