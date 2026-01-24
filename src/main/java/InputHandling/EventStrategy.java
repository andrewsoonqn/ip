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
        if (from == null) {
            throw new ChatbotArgumentException("Event start time is mandatory.");
        }

        String to = flagValues.get("to");
        if (to == null) {
            throw new ChatbotArgumentException("Event end time is mandatory.");
        }

        return new Event(flagValues.get("taskDescription"), flagValues.get("from"), flagValues.get("to"));
    }

    @Override
    public String getExampleUsage() {
        return "event attend meeting \\from Mon 10AM \\to Mon 12PM";
    }
}