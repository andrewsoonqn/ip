package arnold.inputhandling;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.tasks.Event;
import arnold.tasks.Task;

public class EventStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        String from = flagValues.get("from");
        if (from == null || from.isBlank()) {
            throw new ChatbotArgumentException("Please provide an event start time.");
        }

        LocalDateTime parsedFrom;
        try {
            parsedFrom = DateTimeParser.parse(from);
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException("Please provide a valid event start time.");
        }

        String to = flagValues.get("to");
        if (to == null || to.isBlank()) {
            throw new ChatbotArgumentException("Please provide an event end time.");
        }

        LocalDateTime parsedTo;
        try {
            parsedTo = DateTimeParser.parse(to);
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException("Please provide a valid event end time.");
        }

        String taskDescription = flagValues.get("taskDescription");
        if (taskDescription == null || taskDescription.isBlank()) {
            throw new ChatbotArgumentException("Please provide an event description.");
        }

        return new Event(taskDescription, parsedFrom, parsedTo);
    }

    @Override
    public String getExampleUsage() {
        return "event attend meeting /from Mon 10AM /to Mon 12PM";
    }
}