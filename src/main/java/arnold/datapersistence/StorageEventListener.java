package arnold.datapersistence;

/**
 * Listener interface for storage events such as load warnings.
 * Implementations can respond to storage issues by notifying the user.
 */
public interface StorageEventListener {
    /**
     * Called when loading data from storage encounters an error.
     * The task list will be empty, but the application can continue.
     *
     * @param message A user-facing message describing what went wrong.
     */
    void onLoadError(String message);
}
