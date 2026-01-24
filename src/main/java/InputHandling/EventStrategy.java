package InputHandling;

import Messaging.Messenger;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.TaskList;

import java.util.Map;

public class EventStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        return new Event(flagValues.get("taskDescription"), flagValues.get("from"), flagValues.get("to"));
    }
}