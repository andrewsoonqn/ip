package arnold.datapersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.stream.Stream;

import arnold.inputhandling.InputProcessor;
import arnold.messaging.NullMessenger;
import arnold.tasks.TaskList;

/**
 * Concrete implementation of Storage that uses a file for persistence.
 */
public class TaskFileStorage implements Storage {
    private final String filePath;

    /**
     * Initializes a new instance of TaskFileStorage.
     *
     * @param filePath The path to the file used for persistence.
     */
    public TaskFileStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void load(TaskList taskList) {
        InputProcessor inputProcessor = new InputProcessor(new NullMessenger(), taskList);
        Path path = Path.of(filePath);

        try (Stream<String> commands = Files.lines(path)) {
            // Process each line as a command
            commands.forEach(inputProcessor::processInput);
        } catch (NoSuchFileException e) {
            // File doesn't exist yet, so create it'
            FileWriter.createDirectories(path);
            FileWriter.createFile(path.toString());
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save(TaskList taskList) {
        String data = taskList.getTasksAsCommands();

        Path path = Path.of(filePath);

        FileWriter.createDirectories(path);
        FileWriter.writeToFilePath(filePath, data);
    }
}
