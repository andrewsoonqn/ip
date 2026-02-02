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

        FileWriter.createDirectories(path);
        FileWriter.writeToFilePath(filePath, data);
    }
}
