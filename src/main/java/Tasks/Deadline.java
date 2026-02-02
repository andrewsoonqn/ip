package Tasks;

import InputHandling.DateTimeParser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime by;

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
