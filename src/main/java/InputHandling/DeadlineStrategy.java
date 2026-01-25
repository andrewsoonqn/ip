package InputHandling;

import ChatbotExceptions.ChatbotArgumentException;
import Tasks.Deadline;
import Tasks.Task;

import java.util.Map;

public class DeadlineStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        String by = flagValues.get("by");
        if (by == null) {
            throw new ChatbotArgumentException("Deadline is mandatory.");
        }
        return new Deadline(flagValues.get("taskDescription"), by);
    }

    @Override
    public String getExampleUsage() {
        return "deadline submit report /by Sun 11PM";
    }

}