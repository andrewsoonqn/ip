package arnold.tasks;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import arnold.inputhandling.parsing.DateTimeParser;
import arnold.tasks.utils.TaskType;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    @JsonProperty("from")
    private LocalDateTime from;

    @JsonProperty("to")
    private LocalDateTime to;

    /**
     * Initializes a new instance of an event task.
     *
     * @param description The description of the task.
     * @param from The start date and time.
     * @param to The end date and time.
     */
    @JsonCreator
    public Event(
        @JsonProperty("description") String description,
        @JsonProperty("from") LocalDateTime from,
        @JsonProperty("to") LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {

        return "[E]" + super.toString()
            + String.format(" (from: %s to: %s)",
            DateTimeParser.formatDateTime(from),
            DateTimeParser.formatDateTime(to));
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EVENT;
    }
}

