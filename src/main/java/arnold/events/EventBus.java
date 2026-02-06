package arnold.events;

/**
 * Manages event publishing and subscription within the application.
 */
// Code adapted from Gemini
// https://gemini.google.com/share/99d8bc847bb9
public class EventBus {
    private static final EventBus instance = new EventBus();
    // For now, only one shutdown handler is supported
    private Runnable shutdownHandler;

    private EventBus() {
    }

    /**
     * Returns the singleton instance of the EventBus.
     *
     * @return The EventBus instance.
     */
    public static EventBus getInstance() {
        return instance;
    }

    /**
     * Registers a handler to be executed on shutdown.
     *
     * @param handler The runnable to execute on shutdown.
     */
    public void registerShutdownHandler(Runnable handler) {
        this.shutdownHandler = handler;
    }

    /**
     * Publishes a shutdown event to all registered handlers.
     */
    public void publishShutdown() {
        if (shutdownHandler != null) {
            shutdownHandler.run();
        }
    }
}
