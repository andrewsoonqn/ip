package arnold.inputhandling.strategies.taskcrudstrategies.create;

import java.time.LocalDateTime;

import arnold.inputhandling.Messages;
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
        LocalDateTime parsedBy = DateTimeParser.parseWithErrorMessage(
            command.getFlag("by"), Messages.invalidDeadline());

        return new Deadline(command.getDescription(), parsedBy);
    }

    @Override
    public String getDescription() {
        return "Add a task with a deadline";
    }

    @Override
    public String getExampleUsage() {
        return "deadline submit report /by 1/12/2026 2359";
    }
}
