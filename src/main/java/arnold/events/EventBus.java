package arnold.events;

// Code adapted from Gemini
// https://gemini.google.com/share/99d8bc847bb9
public class EventBus {
    private static final EventBus instance = new EventBus();
    // For now, only one shutdown handler is supported
    private Runnable shutdownHandler;

    private EventBus() {
    }

    public static EventBus getInstance() {
        return instance;
    }

    public void registerShutdownHandler(Runnable handler) {
        this.shutdownHandler = handler;
    }

    public void publishShutdown() {
        if (shutdownHandler != null) {
            shutdownHandler.run();
        }
    }
}
