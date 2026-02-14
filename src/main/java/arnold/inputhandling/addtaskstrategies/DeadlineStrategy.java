package arnold.inputhandling.addtaskstrategies;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.parsing.ArgParser;
import arnold.inputhandling.parsing.DateTimeParser;
import arnold.tasks.Deadline;
import arnold.tasks.Task;

/**
 * Strategy for adding a deadline task.
 */
public class DeadlineStrategy extends AddTaskStrategy {
    /**
     * Extracts a deadline task from the user input.
     *
     * @param input The user input containing task description and deadline.
     * @return The created deadline task.
     * @throws ChatbotArgumentException If the input format is invalid or missing information.
     */
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
        return "deadline submit report /by 1/12/2026 2359";
    }

}
