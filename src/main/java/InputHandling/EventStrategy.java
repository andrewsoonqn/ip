package InputHandling;

import ChatbotExceptions.ChatbotArgumentException;
import Tasks.Event;
import Tasks.Task;

import java.util.Map;

public class EventStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        String from = flagValues.get("from");
        if (from == null || from.isBlank()) {
            throw new ChatbotArgumentException("Please provide an event start time.");
        }

        String to = flagValues.get("to");
        if (to == null || to.isBlank()) {
            throw new ChatbotArgumentException("Please provide an event end time.");
        }

        return new Event(flagValues.get("taskDescription"), flagValues.get("from"), flagValues.get("to"));
    }

    @Override
    public String getExampleUsage() {
        return "event attend meeting /from Mon 10AM /to Mon 12PM";
    }
}