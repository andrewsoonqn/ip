package arnold.datapersistence;

import arnold.tasks.TaskList;

/**
 * A storage implementation that does nothing. Useful for testing or when persistence is not needed.
 */
public class NullStorage implements Storage {
    @Override
    public void load(TaskList taskList) {
        // Do nothing
    }

    @Override
    public void save(TaskList taskList) {
        // Do nothing
    }
}
