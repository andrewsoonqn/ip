public class Task {
    private final String description;
    private boolean isDone;
    private static int taskCount = 0;
    private int id;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.id = ++taskCount;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return String.format("%d. [%s] %s", id, getStatusIcon(), description);
    }

    public String getDescription() {
        return description;
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }
}
