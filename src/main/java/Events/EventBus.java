package Events;

// Code adapted from Gemini
// https://gemini.google.com/share/99d8bc847bb9
public class EventBus {
    // For now, only one shutdown handler is supported
    private Runnable shutdownHandler;
    private static EventBus instance = new EventBus();

    private EventBus() {}

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
