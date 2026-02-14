package arnold.tasks;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import arnold.inputhandling.parsing.DateTimeParser;
import arnold.tasks.utils.TaskType;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    @JsonProperty("by")
    private LocalDateTime by;

    /**
     * Initializes a new instance of a deadline task.
     *
     * @param description The description of the task.
     * @param by The deadline date and time.
     */
    @JsonCreator
    public Deadline(@JsonProperty("description") String description, @JsonProperty("by") LocalDateTime by) {
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
}
