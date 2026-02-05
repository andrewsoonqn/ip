package inputhandling;

import java.util.Map;

public class ArgParser {
    private static final String FLAG_SIGNIFIER = " /";

    public static String[] getCommandArgs(String text) {
        String[] parts = text.split("\\s+", 2);

        // "Pair"
        return parts.length > 1 ? new String[]{parts[0], parts[1]} : new String[]{parts[0], ""};
    }

    public static Map<String, String> getFlags(String text) {
        Map<String, String> flags = new java.util.HashMap<>();
        String[] parts = text.split(FLAG_SIGNIFIER);

        // The first part will be the task
        String taskDescription = parts[0].strip();
        flags.put("taskDescription", taskDescription);

        // The rest will be flags
        for (int i = 1; i < parts.length; i++) {
            String[] keyValue = parts[i].split("\\s+", 2);
            if (keyValue.length == 2) {
                flags.put(keyValue[0].strip(), keyValue[1].strip());
            }
        }
        return flags;
    }
}
