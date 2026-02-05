package inputhandling;

import chatbotexceptions.ChatbotArgumentException;
import Tasks.Deadline;
import Tasks.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class DeadlineStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        String by = flagValues.get("by");
        if (by == null || by.isBlank()) {
            throw new ChatbotArgumentException("Please provide a deadline.");
        }

        LocalDateTime parsedBy;
        try {
            parsedBy = DateTimeParser.parse(by);
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException("Please provide a valid deadline.");
        }

        String taskDescription = flagValues.get("taskDescription");
        if (taskDescription == null || taskDescription.isBlank()) {
            throw new ChatbotArgumentException("Please provide a task description.");
        }

        return new Deadline(taskDescription, parsedBy);
    }

    @Override
    public String getExampleUsage() {
        return "deadline submit report /by Sun 11PM";
    }

}