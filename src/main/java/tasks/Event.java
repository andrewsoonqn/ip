package tasks;

import inputhandling.DateTimeParser;

import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

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
        return String.format("event %s /from %s /to %s", getDescription(), DateTimeParser.formatDateTime(from), DateTimeParser.formatDateTime(to));
    }
}

