package arnold.datapersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;

/**
 * Concrete implementation of Storage that uses a file for persistence.
 */
public class TaskFileStorage implements Storage {
    private final String filePath;
    private final ObjectMapper objectMapper;

    /**
     * Initializes a new instance of TaskFileStorage.
     *
     * @param filePath The path to the file used for persistence.
     */
    public TaskFileStorage(String filePath) {
        this.filePath = filePath;
        this.objectMapper = createObjectMapper();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        SimpleModule module = new SimpleModule();
        module.addSerializer(Task.class, new TaskSerializer());
        module.addDeserializer(Task.class, new TaskDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

    @Override
    public void load(TaskList taskList) {
        Path path = Path.of(filePath);

        try {
            String json = Files.readString(path);
            if (json.isBlank()) {
                return;
            }
            List<Task> tasks = objectMapper.readValue(json, new TypeReference<List<Task>>() {});
            taskList.loadTasks(tasks);
        } catch (NoSuchFileException e) {
            // File doesn't exist yet, so create it
            FileWriter.createDirectories(path);
            FileWriter.createFile(path.toString());
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save(TaskList taskList) {
        Path path = Path.of(filePath);
        FileWriter.createDirectories(path);

        try {
            String json = objectMapper.writeValueAsString(taskList.getTasks());
            FileWriter.writeToFilePath(filePath, json);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
