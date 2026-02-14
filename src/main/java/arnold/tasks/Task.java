package arnold.tasks;

/**
 * Represents a generic task.
 */
public abstract class Task {
    private final String description;
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

    /**
     * Returns the task formatted as a command.
     *
     * @return The task as a command string.
     */
    public abstract String asCommand();

    /**
     * Returns true if the task is done.
     *
     * @return True if the task is done.
     */
    public boolean isDone() {
        return isDone;
    }
}
