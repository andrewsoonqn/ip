package inputhandling;

import chatbotexceptions.ChatbotArgumentException;
import messaging.Messenger;
import tasks.Task;
import tasks.TaskList;
import tasks.TaskString;

public abstract class AddTaskStrategy implements InputHandlingStrategy {
    protected abstract Task getTask(String input);

    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        if (input.isBlank()) {
            throw new ChatbotArgumentException(
                    String.format("The description for a task cannot be blank.\n" +
                            "Example usage: %s", getExampleUsage()));
        }

        Task addedTask = taskList.addTask(getTask(input));
        msg.printMessage(
                String.format("Got it. I've added this %s:\n", addedTask.getTaskType().toString())
                        + TaskString.withoutIndex(addedTask)
                        + String.format("\nNow you have %d tasks in the list.", taskList.getSize()));
    }

    public abstract String getExampleUsage();
}
