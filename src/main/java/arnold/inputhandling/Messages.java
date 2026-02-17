package arnold.inputhandling;

/**
 * A utility class providing methods to generate pre-defined messages for the Arnold chatbot.
 * These messages include error notifications, task-related responses, and general chatbot interactions.
 * This class is designed to be non-instantiable and operates entirely through static methods.
 */
public final class Messages {
    private Messages() {
    }

    /**
     * Generates a welcome message for the Arnold chatbot.
     *
     * @return A message welcoming the user and providing initial instructions for using the chatbot.
     */
    public static String welcomeMessage() {
        return "Hello, I'm Arnold! Type 'help' to see a list of commands.";
    }

    /**
     * Generates an error message indicating that a task with the provided ID cannot be found.
     * The message includes the valid range of task IDs based on the current task count.
     * For example, can be used when index is out of bounds, or when a non-integer is provided.
     *
     * @param taskCount The total number of tasks currently in the list, used to calculate
     *     the valid range of task IDs.
     * @return A formatted error message specifying the valid range for task IDs.
     */
    public static String invalidTaskId(int taskCount) {
        return String.format(
            "I am unable to find a task with that ID. "
                + "\nTask ID must be an integer between 1 and %d.", taskCount);
    }

    /**
     * Generates an error message indicating that the provided command is not recognized.
     * This message includes a suggestion to type 'help' for a list of valid commands.
     *
     * @return A formatted error message stating that the command is invalid
     *     and providing guidance for accessing valid commands.
     */
    public static String noSuchCommand() {
        return "Sorry, I don't recognise that command!"
            + "\nType 'help' for a list of available commands.";
    }

    /**
     * Generates an error message indicating that the provided deadline is invalid.
     *
     * @return A string containing the error message.
     */
    public static String invalidDeadline() {
        return "Please provide a valid deadline.";
    }

    /**
     * Generates an error message indicating that the event start time provided is invalid.
     *
     * @return A string containing the error message.
     */
    public static String invalidEventStart() {
        return "Please provide a valid event start time.";
    }

    /**
     * Generates an error message indicating that the provided event end time is invalid.
     *
     * @return A string containing the error message.
     */
    public static String invalidEventEnd() {
        return "Please provide a valid event end time.";
    }

    public static String bye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Generates a message indicating that a task has been added to the list.
     *
     * @param taskType The type of the task being added (e.g., "todo", "event", "deadline").
     * @param taskString The description of the task that has been added.
     * @param taskCount The total number of tasks in the list after the addition.
     * @return A formatted message confirming the addition of the task and displaying the updated task count.
     */
    public static String taskAdded(String taskType, String taskString, int taskCount) {
        return String.format("Got it. I've added this %s:\n%s\nNow you have %d tasks in the list.",
            taskType, taskString, taskCount);
    }

    public static String taskMarked(String taskString) {
        return String.format("Nice! I've marked this task as done:\n%s", taskString);
    }

    public static String taskUnmarked(String taskString) {
        return String.format("OK, I've marked this task as not done yet:\n%s", taskString);
    }

    /**
     * Generates a message indicating that a task has been removed from the list.
     *
     * @param taskString The description of the task that has been removed.
     * @param taskCount The number of remaining tasks in the list after the removal.
     * @return A formatted message confirming the removal of the task and displaying the updated task count.
     */
    public static String taskRemoved(String taskString, int taskCount) {
        return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.",
            taskString, taskCount);
    }

    public static String taskList(String listString) {
        return String.format("Here are the tasks in your list:\n%s", listString);
    }

    /**
     * Formats and generates a message displaying tasks that match a given search query.
     *
     * @param listString The formatted string representing the list of tasks that match
     *     the search criteria.
     * @return A formatted message showing the matching tasks.
     */
    public static String taskFind(String listString) {
        return String.format("Here are the matching tasks in your list:\n%s", listString);
    }

    /**
     * Generates an error message indicating that a task description cannot be blank.
     *
     * @return A formatted error message indicating that the description is blank.
     */
    public static String blankDescription() {
        return "The description for a task cannot be blank.";
    }

    /**
     * Generates an error message indicating that a required command flag is missing.
     * The message includes the missing flag name to help the user identify the issue.
     *
     * @param flagName The name of the missing flag for which the error message is being generated.
     * @return A formatted error message specifying the missing flag.
     */
    public static String missingFlag(String flagName) {
        return String.format("You are missing a required flag: /%s", flagName);
    }

    /**
     * Generates an error message indicating that a task index is out of bounds.
     * The message specifies the valid range of task indices based on the total number of tasks.
     *
     * @param taskCount The total number of tasks currently in the list, used to calculate
     *     the valid range of task indices.
     * @return A formatted error message specifying the valid range for task indices.
     */
    public static String taskIndexOutOfBounds(int taskCount) {
        return String.format("The task index must be between 1 and %d.", taskCount);
    }

    public static String helpHeader() {
        return "Available commands:\n";
    }
}
