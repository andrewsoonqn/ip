package datapersistence;

import tasks.TaskList;

import java.nio.file.Path;

public class DataSaver {
    public static void saveData(String filePath, TaskList taskList) {
        String data = taskList.getTasksAsCommands();

        Path path = Path.of(filePath);

        FileWriter.createDirectories(path);
        FileWriter.writeToFilePath(filePath, data);
    }
}
