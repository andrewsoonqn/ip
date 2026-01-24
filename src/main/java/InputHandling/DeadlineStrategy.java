package InputHandling;

import Messaging.Messenger;
import Tasks.Deadline;
import Tasks.Task;
import Tasks.TaskList;
import Tasks.Todo;

import java.util.Map;

public class DeadlineStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        Map<String, String> flagValues = ArgParser.getFlags(input);

        Task task = new Deadline(flagValues.get("taskDescription"), flagValues.get("by"));
        String message = taskList.addTask(task);
        msg.printMessage(message);
    }
}