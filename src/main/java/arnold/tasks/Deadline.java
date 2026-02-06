package arnold.tasks;

import arnold.inputhandling.DateTimeParser;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Initializes a new instance of a deadline task.
     *
     * @param description The description of the task.
     * @param by The deadline date and time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + String.format(" (by: %s)", DateTimeParser.formatDateTime(by));
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    @Override
    public String asCommand() {
        return String.format("deadline %s /by %s", getDescription(), DateTimeParser.formatDateTime(by));
    }
}
