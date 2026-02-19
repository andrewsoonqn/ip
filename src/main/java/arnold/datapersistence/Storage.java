package arnold.datapersistence;

import arnold.chatbotexceptions.StorageException;
import arnold.tasks.utils.TaskList;

/**
 * Interface for loading and saving task list data.
 */
public interface Storage {
    /**
     * Loads tasks into the provided TaskList. If loading fails due to corrupted or
     * unreadable data, the implementation should start with an empty task list and
     * notify any registered listeners.
     *
     * @param taskList The TaskList to populate.
     */
    void load(TaskList taskList);

    /**
     * Saves the provided TaskList.
     *
     * @param taskList The TaskList to save.
     * @throws StorageException If the data could not be saved.
     */
    void save(TaskList taskList);

    /**
     * Registers a listener to be notified of storage events such as load errors.
     *
     * @param listener The listener to register.
     */
    void setEventListener(StorageEventListener listener);
}
