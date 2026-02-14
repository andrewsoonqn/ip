package arnold.datapersistence;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import arnold.tasks.Deadline;
import arnold.tasks.Event;
import arnold.tasks.Task;
import arnold.tasks.Todo;

public class TaskDeserializer extends StdDeserializer<Task> {
    public TaskDeserializer() {
        super(Task.class);
    }

    @Override
    public Task deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        String type = node.get("type").asText();
        String description = node.get("description").asText();
        boolean isDone = node.get("isDone").asBoolean();

        Task task;
        switch (type) {
        case "todo":
            task = new Todo(description);
            break;
        case "deadline":
            LocalDateTime by = LocalDateTime.parse(node.get("by").asText());
            task = new Deadline(description, by);
            break;
        case "event":
            LocalDateTime from = LocalDateTime.parse(node.get("from").asText());
            LocalDateTime to = LocalDateTime.parse(node.get("to").asText());
            task = new Event(description, from, to);
            break;
        default:
            throw new IOException("Unknown task type: " + type);
        }

        if (isDone) {
            task.mark();
        }

        return task;
    }
}
