package arnold.inputhandling.strategies.taskcrudstrategies.create;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.parsing.DateTimeParser;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Deadline;
import arnold.tasks.Task;

/**
 * Strategy for adding a deadline task.
 */
public class DeadlineStrategy extends CreateTaskStrategy {
    /**
     * Extracts a deadline task from the parsed command.
     *
     * @param command The parsed command containing task description and /by flag.
     * @return The created deadline task.
     * @throws ChatbotArgumentException If the deadline date format is invalid.
     */
    @Override
    protected Task getTask(ParsedCommand command) {
        LocalDateTime parsedBy;
        try {
            parsedBy = DateTimeParser.parse(command.getFlag("by"));
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException("Please provide a valid deadline.");
        }

        return new Deadline(command.getDescription(), parsedBy);
    }

    @Override
    public String getExampleUsage() {
        return "deadline submit report /by 1/12/2026 2359";
    }
}
