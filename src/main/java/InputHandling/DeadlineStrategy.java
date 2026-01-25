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
        if (by == null || by.isBlank()) {
            throw new ChatbotArgumentException("Please provide a deadline.");
        }
        return new Deadline(flagValues.get("taskDescription"), by);
    }

    @Override
    public String getExampleUsage() {
        return "deadline submit report /by Sun 11PM";
    }

}