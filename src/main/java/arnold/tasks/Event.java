package arnold.tasks;

import java.time.LocalDateTime;

import arnold.inputhandling.parsing.DateTimeParser;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Initializes a new instance of an event task.
     *
     * @param description The description of the task.
     * @param from The start date and time.
     * @param to The end date and time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
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

    @Override
    public String asCommand() {
        return String.format("event %s /from %s /to %s", getDescription(), DateTimeParser.formatDateTime(from),
            DateTimeParser.formatDateTime(to));
    }
}

