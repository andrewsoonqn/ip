package arnold.datapersistence;

import arnold.tasks.TaskList;

/**
 * Interface for loading and saving task list data.
 */
public interface Storage {
    /**
     * Loads tasks into the provided TaskList.
     * @param taskList The TaskList to populate.
     */
    void load(TaskList taskList);

    /**
     * Saves the provided TaskList.
     * @param taskList The TaskList to save.
     */
    void save(TaskList taskList);
}
