package arnold.inputhandling;

/**
 * Centralized store for all user-facing message strings.
 * All messages are exposed as static methods with typed parameters
 * for consistency and to encapsulate formatting logic.
 */
public final class Messages {
    private Messages() {
    }

    public static String invalidTaskId() {
        return "I am unable to find a task with that ID.";
    }

    public static String noSuchCommand() {
        return "Sorry, I don't recognise that command!";
    }

    public static String invalidDeadline() {
        return "Please provide a valid deadline.";
    }

    public static String invalidEventStart() {
        return "Please provide a valid event start time.";
    }

    public static String invalidEventEnd() {
        return "Please provide a valid event end time.";
    }

    public static String bye() {
        return "Bye. Hope to see you again soon!";
    }

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

    public static String taskRemoved(String taskString, int taskCount) {
        return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.",
            taskString, taskCount);
    }

    public static String taskList(String listString) {
        return String.format("Here are the tasks in your list:\n%s", listString);
    }

    public static String taskFind(String listString) {
        return String.format("Here are the matching tasks in your list:\n%s", listString);
    }

    public static String blankDescription(String exampleUsage) {
        return String.format("The description for a task cannot be blank.\nExample usage: %s",
            exampleUsage);
    }

    public static String missingFlag(String flagName) {
        return String.format("You are missing a required flag: /%s", flagName);
    }
}
