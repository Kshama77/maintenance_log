# MaintenanceLog 🏡

A beginner-friendly Java console app for keeping track of home and property maintenance tasks.

## Features

- View all maintenance tasks in a clean table
- Add a task with an area, due date, priority, and optional notes
- Mark tasks as completed
- View pending and overdue tasks
- Delete tasks with confirmation
- View a small summary with task counts and completion progress
- Includes sample tasks so the app is ready to try

## Built with

- Java
- Classes and objects
- `ArrayList`
- Loops and `switch`
- Methods
- `LocalDate`
- Input validation

## Requirements

- Java 17 or later

## Run the app

Open a terminal in the project folder and compile the Java files:

```bash
javac -d out src/com/maintenancelog/*.java
```

Run the app:

```bash
java -cp out com.maintenancelog.MaintenanceLogApp
```

You can also open the `src` folder in IntelliJ IDEA or Eclipse and run `MaintenanceLogApp.java`.

## Notes

This is a simple learning project. Tasks are stored in memory, so they reset when the app exits. Saving tasks to a file or database could be a future improvement.

## Example task ideas

- Replace HVAC filter
- Inspect smoke alarms
- Check for leaks under sinks
- Clean gutters
- Service the lawn mower
- Test outdoor lights

## Future improvements

- Save tasks between app runs
- Edit task details
- Search tasks by area or priority
- Add maintenance costs
