package arnold.datapersistence;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import arnold.tasks.Deadline;
import arnold.tasks.Event;
import arnold.tasks.Task;
import arnold.tasks.Todo;

public class TaskSerializer extends StdSerializer<Task> {
    public TaskSerializer() {
        super(Task.class);
    }

    @Override
    public void serialize(Task task, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", task.getTaskType().toString());
        gen.writeStringField("description", task.getDescription());
        gen.writeBooleanField("isDone", task.isDone());

        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            gen.writeStringField("by", deadline.getBy().toString());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            gen.writeStringField("from", event.getFrom().toString());
            gen.writeStringField("to", event.getTo().toString());
        }

        gen.writeEndObject();
    }
}
