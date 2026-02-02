package DataPersistence;

import Tasks.TaskList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DataSaver {
    public static void saveData(String filePath, TaskList taskList) {
        String data = taskList.getTasksAsCommands();

        Path path = Path.of(filePath);
        System.out.println(path.toAbsolutePath());

        // Parent directory will be null if not specified, e.g. "data.txt"
        // Prevent NullPointerException
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                System.err.println("Error creating directories: " + e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            Files.write(path, data.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
