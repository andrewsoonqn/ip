package Tasks;

public abstract class Task {
    private final String description;
    private boolean isDone;
    private static int taskCount = 0;
    private final int id;

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
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }
}
