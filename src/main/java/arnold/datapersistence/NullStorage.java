package arnold.datapersistence;

import arnold.tasks.utils.TaskList;

/**
 * A storage implementation that does nothing. Useful for testing or when persistence is not needed.
 */
public class NullStorage implements Storage {
    /**
     * Loads tasks into the provided TaskList.
     *
     * @param taskList The TaskList to populate.
     */
    @Override
    public void load(TaskList taskList) {
        // Do nothing
    }

    /**
     * Saves the provided TaskList.
     *
     * @param taskList The TaskList to save.
     */
    @Override
    public void save(TaskList taskList) {
        // Do nothing
    }

    /**
     * Registers a listener to be notified of storage events.
     *
     * @param listener The listener to register.
     */
    @Override
    public void setEventListener(StorageEventListener listener) {
        // Do nothing
    }
}
