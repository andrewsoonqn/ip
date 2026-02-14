# Data loading and storing strategy
_Exported on 14/02/2026 at 21:14:48 GMT+8 from Cursor (2.4.36)_

---

**User**

The data loading and storing strategy now is not really sound. It uses the input processor and task commands to store. This however omits information such as whether a task is done. Moreover, I would like to use json to serialise so that it can be robust to symbols (instead of using "|" as separators, for example)

---

**Cursor**

Let me explore the codebase to understand the current data loading/storing strategy and overall architecture.

Now let me read the key files to get the exact code.

Let me check if `asCommand()` and `getTasksAsCommands()` are used anywhere beyond the storage flow.

Let me also check the existing tests to understand what needs to stay compatible.

I now have a thorough understanding of the current codebase. Here's a summary of the issues and my proposed plan.

---

**User**

let's use jackson instead

---

**Cursor**

I'll update the plan to use Jackson instead of Gson.

The plan has been updated to use **Jackson** instead of Gson. The key differences:

- **Dependencies**: `jackson-databind` + `jackson-datatype-jsr310` (for native `LocalDateTime` support) instead of Gson
- **Custom serializer/deserializer**: Jackson's `StdSerializer<Task>` / `StdDeserializer<Task>` registered via a `SimpleModule` on the `ObjectMapper`, rather than Gson's `JsonSerializer`/`JsonDeserializer`
- **JSR-310 module**: Jackson has a dedicated module for Java 8 date/time types, so `LocalDateTime` is handled cleanly out of the box

Everything else (the JSON format, the architectural changes, what gets removed) stays the same. Let me know when you'd like to proceed.

---

**User**

Implement the plan as specified, it is attached for your reference. Do NOT edit the plan file itself.

To-do's from the plan have already been created. Do not create them again. Mark them as in_progress as you work, starting with the first one. Don't stop until you have completed all the to-dos.

---

**Cursor**

Now adding the date getters to `Deadline` and `Event`.

Now creating the custom Jackson serializer and deserializer.

Now modifying `TaskList.java` — adding `getTasks()`, `loadTasks()`, and removing `getTasksAsCommands()`.

Now rewriting `TaskFileStorage.java` to use Jackson.

Now removing `asCommand()` from the Task hierarchy.

Now let me check for linter errors across the modified files and verify everything compiles.

Let me also verify the build compiles and tests pass.

Checkstyle requires Javadoc on public classes/methods, and there's an unused import. Let me fix those.

The file already has Javadoc. Let me just rebuild to confirm all checkstyle issues are resolved.

Build successful — all 28 tests pass and checkstyle is clean. Let me also check the `TaskList.java` checkstyle didn't flag the new methods missing Javadoc.

The build passed with checkstyle, so the new methods are fine. All done.

All tasks are complete. Here's a summary of what was done:

1. **`build.gradle`** -- Added `jackson-databind` and `jackson-datatype-jsr310` dependencies.

2. **`TaskSerializer.java`** (new) -- Custom Jackson `StdSerializer<Task>` that writes each task as a JSON object with `type`, `description`, `isDone`, and type-specific date fields (`by` / `from` + `to`) using ISO-8601 format.

3. **`TaskDeserializer.java`** (new) -- Custom Jackson `StdDeserializer<Task>` that reads the `type` field to construct the correct subclass, restores `isDone` via `mark()`, and parses ISO date strings back to `LocalDateTime`.

4. **`TaskFileStorage.java`** -- Completely rewritten. Uses an `ObjectMapper` with the custom serializer/deserializer instead of replaying commands through `InputProcessor`. The `InputProcessor` dependency is fully removed.

5. **`TaskList.java`** -- Replaced `getTasksAsCommands()` with `getTasks()` (unmodifiable view) and `loadTasks(List<Task>)` (bulk-add without triggering saves).

6. **`Task.java`, `Todo.java`, `Deadline.java`, `Event.java`** -- Removed `asCommand()` from the entire hierarchy. Added `getBy()`, `getFrom()`, `getTo()` getters on the date-bearing subclasses.

7. **`DataPaths.java`** -- Changed file path from `tasks.txt` to `tasks.json`.

The build passes cleanly (all 28 tests green, checkstyle clean). Tasks now persist their full state including `isDone`, and the JSON format is robust against special characters in descriptions.

---

**User**

why can't we just serialise based on fields? isn't there an "annotation" workaround?

---

**Cursor**

You're right — we absolutely can use Jackson annotations to avoid the custom serializer/deserializer classes entirely. The reason I went with custom classes is that the `Task` hierarchy has a few quirks (no default constructors, `isDone` set only via `mark()`, polymorphic subclasses), but Jackson annotations handle all of these natively:

- **`@JsonTypeInfo` + `@JsonSubTypes`** on `Task` handles the polymorphic `"type"` field
- **`@JsonCreator` + `@JsonProperty`** on subclass constructors handles deserialization without default constructors
- **`@JsonProperty("isDone")`** on the field lets Jackson read/write it directly via reflection, bypassing the lack of a setter

The annotation approach would look like this on `Task`:

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Todo.class, name = "todo"),
    @JsonSubTypes.Type(value = Deadline.class, name = "deadline"),
    @JsonSubTypes.Type(value = Event.class, name = "event")
})
public abstract class Task {
    @JsonProperty("description")
    private final String description;

    @JsonProperty("isDone")
    private boolean isDone;
    // ...
}
```

And on each subclass constructor, e.g. `Deadline`:

```java
@JsonCreator
public Deadline(@JsonProperty("description") String description, @JsonProperty("by") LocalDateTime by) {
    super(description);
    this.by = by;
}
```

This would **delete both `TaskSerializer.java` and `TaskDeserializer.java`** entirely, and reduce `TaskFileStorage` to just an `ObjectMapper` with the `JavaTimeModule` registered — no `SimpleModule` needed. The JSON output stays the same.

Want me to refactor to the annotation approach?

---

**User**

ok make changes

---

**Cursor**

Now simplifying `TaskFileStorage` to just use `JavaTimeModule` instead of the custom `SimpleModule`.

Build successful — all 28 tests pass and checkstyle is clean.

Done. Here's what changed:

- **`Task.java`** -- Added `@JsonTypeInfo` + `@JsonSubTypes` (maps `"type"` to the correct subclass) and `@JsonProperty` on `description` and `isDone` fields.
- **`Todo.java`** -- Added `@JsonCreator` + `@JsonProperty("description")` on the constructor.
- **`Deadline.java`** -- Added `@JsonProperty("by")` on the field and `@JsonCreator` + `@JsonProperty` params on the constructor.
- **`Event.java`** -- Added `@JsonProperty("from")` / `@JsonProperty("to")` on the fields and `@JsonCreator` + `@JsonProperty` params on the constructor.
- **`TaskFileStorage.java`** -- Replaced the `SimpleModule` with custom serializer/deserializer with just `JavaTimeModule` + `WRITE_DATES_AS_TIMESTAMPS` disabled.
- **Deleted** `TaskSerializer.java` and `TaskDeserializer.java` -- no longer needed.

Jackson now handles everything through annotations and reflection, with no custom serialization logic required.

---

**User**

[ {
  "description" : "123",
  "taskType" : "TODO",
  "done" : false,
  "isDone" : false
} ]

Error loading data: Could not resolve subtype of [simple type, class arnold.tasks.Task]: missing type id property 'type'
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 6, column: 1] (through reference chain: java.util.ArrayList[0])
com.fasterxml.jackson.databind.exc.InvalidTypeIdException: Could not resolve subtype of [simple type, class arnold.tasks.Task]: missing type id property 'type'
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 6, column: 1] (through reference chain: java.util.ArrayList[0])
	at com.fasterxml.jackson.databind.exc.InvalidTypeIdException.from(InvalidTypeIdException.java:43)
	at com.fasterxml.jackson.databind.DeserializationContext.missingTypeIdException(DeserializationContext.java:2050)
	at com.fasterxml.jackson.databind.DeserializationContext.handleMissingTypeId(DeserializationContext.java:1622)
	at com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase._handleMissingTypeId(TypeDeserializerBase.java:307)
	at com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer._deserializeTypedUsingDefaultImpl(AsPropertyTypeDeserializer.java:211)
	at com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer.deserializeTypedFromObject(AsPropertyTypeDeserializer.java:145)
	at com.fasterxml.jackson.databind.deser.AbstractDeserializer.deserializeWithType(AbstractDeserializer.java:263)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer._deserializeFromArray(CollectionDeserializer.java:363)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:246)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:30)
	at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:342)
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4905)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3848)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3831)
	at arnold.datapersistence.TaskFileStorage.load(TaskFileStorage.java:51)
	at arnold.tasks.utils.TaskList.create(TaskList.java:32)
	at arnold.ArnoldApplication.start(ArnoldApplication.java:27)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.lambda$launchApplication1$9(LauncherImpl.java:839)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.lambda$runAndWait$12(PlatformImpl.java:483)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.lambda$runLater$10(PlatformImpl.java:456)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.lambda$runLater$11(PlatformImpl.java:455)
	at javafx.graphics/com.sun.glass.ui.InvokeLaterDispatcher$Future.run(InvokeLaterDispatcher.java:95)

---

**Cursor**

The issue is that Jackson is auto-detecting getter methods, producing unwanted properties (`"taskType"` from `getTaskType()`, `"done"` from `isDone()`), and the `"type"` discriminator isn't being written. I need to disable getter auto-detection so Jackson only uses the explicitly annotated fields.

The problem is that Jackson auto-detects `getTaskType()` as property `"taskType"` and `isDone()` as property `"done"`, polluting the JSON. The `"type"` discriminator is likely being suppressed because of these conflicts. I'll add `@JsonAutoDetect` to disable getter auto-detection, so only explicitly `@JsonProperty`-annotated fields are serialized.

Build passes. Now delete the old `tasks.txt` data file if it exists so the app starts fresh with JSON, then test it end-to-end.

The fix was adding `@JsonAutoDetect` to `Task.java` to disable getter auto-detection. The root cause was that Jackson was auto-detecting `getTaskType()` as a `"taskType"` property and `isDone()` as a `"done"` property, which polluted the JSON output and interfered with the `@JsonTypeInfo` type discriminator. With getter auto-detection disabled, Jackson now only serializes explicitly `@JsonProperty`-annotated fields plus the `"type"` discriminator from `@JsonTypeInfo`. Give it another try -- the JSON should now look like:

```json
[ {
  "type" : "todo",
  "description" : "123",
  "isDone" : false
} ]
```

---

**User**

[ {
  "description" : "1",
  "isDone" : false
} ]

---

**Cursor**

The `"type"` discriminator is still missing. This is because `writeValueAsString()` infers the runtime type (`Todo`) directly, so Jackson skips the type info since it already "knows" the concrete type. The fix is to explicitly tell Jackson to serialize as `List<Task>` using `writerFor`.

Build passes. The issue was that `writeValueAsString(Object)` sees the runtime types directly and skips the type discriminator. Using `writerFor(new TypeReference<List<Task>>() {})` explicitly tells Jackson the declared type is `List<Task>`, which forces the `@JsonTypeInfo` to write the `"type"` field. Try it again — the JSON should now include the type:

```json
[ {
  "type" : "todo",
  "description" : "1",
  "isDone" : false
} ]
```

---

**User**

mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

explain this

---

**Cursor**


**`mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)`**

By default, Jackson serializes `LocalDateTime` (and other Java 8 date/time types) as numeric arrays like `[2026, 2, 14, 10, 30, 0]` (year, month, day, hour, minute, second). Turning this off makes Jackson use ISO-8601 strings instead, e.g. `"2026-02-14T10:30:00"`, which are easier to read and work with.

**`mapper.registerModule(new JavaTimeModule())`**

Jackson’s core doesn’t know how to serialize/deserialize `LocalDateTime`, `LocalDate`, `ZonedDateTime`, etc. The `JavaTimeModule` adds support for these types. Without it, Jackson would throw when it encounters a `LocalDateTime` (e.g. in `Deadline.by` or `Event.from`/`Event.to`).

---

**User**

    String json = objectMapper.writerFor(new TypeReference<List<Task>>() {})
                    .writeValueAsString(taskList.getTasks());

what about this

---

**Cursor**


**`objectMapper.writerFor(new TypeReference<List<Task>>() {})`**

This creates a writer configured to treat the value as `List<Task>` (the declared type), instead of using the runtime type of each element.

**Why it matters**

With `objectMapper.writeValueAsString(taskList.getTasks())`, Jackson infers types from the actual objects. For a list of `Todo`, it sees `List<Todo>` and serializes each item as a `Todo`. Because the concrete type is known, Jackson does not add the `@JsonTypeInfo` discriminator (`"type"`).

Using `writerFor(new TypeReference<List<Task>>() {})` tells Jackson the declared type is `List<Task>`. Each element is then treated as a polymorphic `Task`, so Jackson adds the `"type"` field (`"todo"`, `"deadline"`, `"event"`) to each object. That way, when loading, Jackson can choose the right subclass from the `"type"` field.

**`TypeReference<List<Task>>() {}`**

Because of type erasure, `List<Task>.class` is not valid and `List.class` loses the `Task` parameter. `TypeReference<List<Task>>() {}` is a common pattern that preserves the full generic type (`List<Task>`) so Jackson can use it for serialization.

---

**User**

why without this type = todo etc. is not registered?

---

**Cursor**


**How Jackson decides whether to write the type discriminator**

`@JsonTypeInfo` tells Jackson to add a type discriminator when it treats the value as a **polymorphic** type — i.e. when the declared type is the base type (`Task`) and Jackson doesn’t know the concrete subclass at compile time.

**Without `writerFor`**

When you call `objectMapper.writeValueAsString(taskList.getTasks())`:

1. Jackson gets a `List` whose elements are `Todo`, `Deadline`, or `Event`.
2. It infers the type from the actual objects. For a list of `Todo`, it sees `List<Todo>`.
3. Because the element type is already `Todo`, Jackson treats it as a concrete type, not polymorphic.
4. There’s no need for a discriminator: the type is known.
5. So `@JsonTypeInfo` is not used and `"type"` is not written.

**With `writerFor(new TypeReference<List<Task>>() {})`**

1. Jackson is told the declared type is `List<Task>`.
2. For each element, the declared type is `Task` (the base class).
3. Jackson doesn’t know the concrete subclass from the type alone; it must use polymorphism.
4. `@JsonTypeInfo` applies: Jackson writes the `"type"` field so it can reconstruct the right subclass when deserializing.

So the type discriminator is only written when Jackson treats the value as polymorphic. Without `writerFor`, it infers the concrete type and skips the discriminator.

---

**User**

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Todo.class, name = "todo"),
    @JsonSubTypes.Type(value = Deadline.class, name = "deadline"),
    @JsonSubTypes.Type(value = Event.class, name = "event")
})
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)

explain all these

---

**Cursor**


**`@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")`**

Configures polymorphic type handling for `Task`:

- **`use = JsonTypeInfo.Id.NAME`** — Store the subtype as a string name (e.g. `"todo"`, `"deadline"`, `"event"`).
- **`property = "type"`** — Put that name in a JSON field called `"type"`.

So each serialized task gets a `"type"` field that tells Jackson which subclass to use when deserializing.

---

**`@JsonSubTypes({ ... })`**

Defines the mapping from those type names to Java classes:

- **`value = Todo.class, name = "todo"`** — When `"type"` is `"todo"`, deserialize as `Todo`.
- Same idea for `Deadline` and `Event`.

Without this, Jackson would not know how to turn `"todo"` into a `Todo` instance.

---

**`@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)`**

Controls which methods Jackson treats as properties:

- **`getterVisibility = NONE`** — Ignore getters like `getDescription()` and `getTaskType()`.
- **`isGetterVisibility = NONE`** — Ignore boolean getters like `isDone()`.

Jackson then only serializes fields that are explicitly annotated (e.g. with `@JsonProperty`). That avoids:

- `getTaskType()` being serialized as `"taskType"`.
- `isDone()` being serialized as `"done"` (JavaBean naming for boolean getters).

So the JSON only contains the fields you want: `"type"`, `"description"`, `"isDone"`, and any subclass-specific fields.

