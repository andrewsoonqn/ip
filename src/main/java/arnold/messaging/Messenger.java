package arnold.messaging;

public abstract class Messenger {
    protected void indentPrint(String text) {
        System.out.print("    ");
        System.out.println(text);
    }

    protected void line() {
        indentPrint("_".repeat(60));
    }

    public abstract void printMessage(String message);

    public abstract void hi();
}
