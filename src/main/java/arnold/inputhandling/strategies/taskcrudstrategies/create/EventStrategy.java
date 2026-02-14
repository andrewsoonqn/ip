package arnold.inputhandling.strategies.taskcrudstrategies.create;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.parsing.DateTimeParser;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Event;
import arnold.tasks.Task;

/**
 * Strategy for adding an event task.
 */
public class EventStrategy extends CreateTaskStrategy {
    /**
     * Extracts an event task from the parsed command.
     *
     * @param command The parsed command containing task description and /from, /to flags.
     * @return The created event task.
     * @throws ChatbotArgumentException If the event time format is invalid.
     */
    @Override
    protected Task getTask(ParsedCommand command) {
        LocalDateTime parsedFrom;
        try {
            parsedFrom = DateTimeParser.parse(command.getFlag("from"));
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException("Please provide a valid event start time.");
        }

        LocalDateTime parsedTo;
        try {
            parsedTo = DateTimeParser.parse(command.getFlag("to"));
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException("Please provide a valid event end time.");
        }

        return new Event(command.getDescription(), parsedFrom, parsedTo);
    }

    @Override
    public String getExampleUsage() {
        return "event attend meeting /from 1/12/2026 1000 /to 1/12/2026 1200";
    }
}
