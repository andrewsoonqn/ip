package InputHandling;

import Messaging.Messenger;
import Tasks.Task;
import Tasks.TaskList;
import Tasks.Todo;

public class TodoStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        return new Todo(input);
    }
}