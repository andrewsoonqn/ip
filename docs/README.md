# Arnold User Guide

Arnold is a friendly task management chatbot that helps you keep track of your todos, deadlines, and events. With a simple command-line style interface and persistent storage, Arnold makes task management effortless.

![Product screenshot](Ui.png)

## Quick Start

1. Download the latest release of Arnold
2. Double-click the JAR file to launch the application
3. Type commands in the text field and press Enter or click Send
4. Type `help` to see all available commands

## Features

### Adding a Todo: `todo`

Add a simple task without any date or time.

**Format**: `todo <description>`

**Example**: `todo read book`

**Expected output**:
```
Got it. I've added this todo:
  [T][ ] read book
Now you have 1 task in the list.
```

---

### Adding a Deadline: `deadline`

Add a task with a due date and time.

**Format**: `deadline <description> /by <date> <time>`

**Example**: `deadline submit report /by 1/12/2026 2359`

**Expected output**:
```
Got it. I've added this deadline:
  [D][ ] submit report (by: 1/12/2026 2359)
Now you have 2 tasks in the list.
```

**Date/Time Format**:
- Date: `day/month/year` (e.g., `25/12/2026`) or `day/month` (year optional)
- Time: `HHMM` format (e.g., `2359` for 11:59 PM) or 1-3 digits (e.g., `9` = 09:00, `930` = 09:30)
- If time is omitted, defaults to 23:59

---

### Adding an Event: `event`

Add a task with both start and end times.

**Format**: `event <description> /from <date> <time> /to <date> <time>`

**Example**: `event attend meeting /from 1/12/2026 1000 /to 1/12/2026 1200`

**Expected output**:
```
Got it. I've added this event:
  [E][ ] attend meeting (from: 1/12/2026 1000 to: 1/12/2026 1200)
Now you have 3 tasks in the list.
```

---

### Listing All Tasks: `list`

Display all tasks in your task list.

**Format**: `list`

**Example**: `list`

**Expected output**:
```
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] submit report (by: 1/12/2026 2359)
3.[E][ ] attend meeting (from: 1/12/2026 1000 to: 1/12/2026 1200)
```

**Task Status Symbols**:
- `[T]` = Todo, `[D]` = Deadline, `[E]` = Event
- `[X]` = Completed, `[ ]` = Not completed

---

### Marking a Task as Done: `mark`

Mark a task as completed.

**Format**: `mark <task_number>`

**Example**: `mark 1`

**Expected output**:
```
Nice! I've marked this task as done:
  [T][X] read book
```

---

### Marking a Task as Not Done: `unmark`

Mark a task as incomplete.

**Format**: `unmark <task_number>`

**Example**: `unmark 1`

**Expected output**:
```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

---

### Deleting a Task: `delete`

Remove a task from your list.

**Format**: `delete <task_number>`

**Example**: `delete 1`

**Expected output**:
```
Noted. I've removed this task:
  [T][ ] read book
Now you have 2 tasks in the list.
```

---

### Finding Tasks: `find`

Search for tasks containing a specific keyword (case-insensitive).

**Format**: `find <keyword>`

**Example**: `find meeting`

**Expected output**:
```
Here are the matching tasks in your list:
1.[E][ ] attend meeting (from: 1/12/2026 1000 to: 1/12/2026 1200)
```

---

### Getting Help: `help`

Display a list of all available commands with examples.

**Format**: `help`

**Example**: `help`

**Expected output**:
A list of all available commands with examples.

---

### Exiting the Application: `bye`

Exit Arnold and close the application.

**Format**: `bye`

**Example**: `bye`

**Expected output**:
```
Bye. Hope to see you again soon!
```

---

## Data Storage

Arnold automatically saves your tasks to `./data/tasks.json` after every command. Your tasks will be restored when you restart the application, so you never lose track of what needs to be done.
