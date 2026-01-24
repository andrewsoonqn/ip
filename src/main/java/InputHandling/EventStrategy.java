package InputHandling;

import Messaging.Messenger;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.TaskList;

import java.util.Map;

public class EventStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        Task task = new Event(flagValues.get("taskDescription"), flagValues.get("from"), flagValues.get("to"));
        String message = taskList.addTask(task);
        msg.printMessage(message);
    }
}