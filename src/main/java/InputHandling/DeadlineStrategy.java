package InputHandling;

import Tasks.Deadline;
import Tasks.Task;

import java.util.Map;

public class DeadlineStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        return new Deadline(flagValues.get("taskDescription"), flagValues.get("by"));
    }
}