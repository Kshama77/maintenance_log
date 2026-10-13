package com.maintenancelog;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaintenanceLogApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Maintenance> tasks = new ArrayList<>();
    private static int nextId = 1;

    public static void main(String[] args) {
        addSampleTasks();

        System.out.println("==============================================");
        System.out.println("          MAINTENANCE LOG");
        System.out.println("       Your home, well looked after");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> showTasks();
                case 2 -> addTask();
                case 3 -> markCompleted();
                case 4 -> showTasksByStatus(false);
                case 5 -> showTasksByStatus(true);
                case 6 -> deleteTask();
                case 7 -> showSummary();
                case 0 -> {
                    running = false;
                    System.out.println("\\nThanks for using Maintenance Log. Keep your home in good shape!");
                }
                default -> System.out.println("Please choose a number from 0 to 7.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\\n--------------- MAIN MENU -------------------");
        System.out.println("1. View all maintenance tasks");
        System.out.println("2. Add a maintenance task");
        System.out.println("3. Mark a task as completed");
        System.out.println("4. View pending tasks");
        System.out.println("5. View overdue tasks");
        System.out.println("6. Delete a task");
        System.out.println("7. View maintenance summary");
        System.out.println("0. Exit");
        System.out.println("---------------------------------------------");
    }

    private static void addTask() {
        System.out.println("\\nADD MAINTENANCE TASK");
        String title = readRequired("Task name (e.g., Clean gutters): ");
        String area = readRequired("Area (e.g., Roof, Kitchen, Garden): ");
        LocalDate dueDate = readDate("Due date (YYYY-MM-DD): ");
        String priority = readPriority();
        System.out.print("Notes (optional): ");
        String notes = scanner.nextLine().trim();

        tasks.add(new Maintenance(nextId++, title, area, dueDate, priority, notes));
        System.out.println("Task added successfully!");
    }

    private static void showTasks() {
        System.out.println("\\nALL MAINTENANCE TASKS");
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet. Add one to get started!");
            return;
        }
        printHeader();
        for (Maintenance task : tasks) System.out.println(task);
        System.out.println("\\nTip: Use option 7 to see your progress at a glance.");
    }

    private static void markCompleted() {
        showTasks();
        if (tasks.isEmpty()) return;
        int id = readInt("\\nEnter the task ID to mark completed: ");
        Maintenance task = findTask(id);
        if (task == null) {
            System.out.println("No task found with ID " + id + ".");
        } else if (task.isCompleted()) {
            System.out.println("That task is already completed.");
        } else {
            task.markCompleted();
            System.out.println("Great job! Task marked as completed.");
        }
    }

    private static void showTasksByStatus(boolean overdueOnly) {
        String heading = overdueOnly ? "OVERDUE TASKS" : "PENDING TASKS";
        System.out.println("\\n" + heading);
        boolean found = false;
        printHeader();
        for (Maintenance task : tasks) {
            if (!task.isCompleted() && (!overdueOnly || task.getDueDate().isBefore(LocalDate.now()))) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) System.out.println(overdueOnly
            ? "No overdue tasks. Nice work!"
            : "No pending tasks. Everything is up to date!");
    }

    private static void deleteTask() {
        showTasks();
        if (tasks.isEmpty()) return;
        int id = readInt("\\nEnter the task ID to delete: ");
        Maintenance task = findTask(id);
        if (task == null) {
            System.out.println("No task found with ID " + id + ".");
            return;
        }
        System.out.print("Delete \"" + task.getTitle() + "\"? (y/n): ");
        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("y")) {
            tasks.remove(task);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void showSummary() {
        int completed = 0;
        int overdue = 0;
        int pending = 0;
        for (Maintenance task : tasks) {
            if (task.isCompleted()) completed++;
            else if (task.getDueDate().isBefore(LocalDate.now())) overdue++;
            else pending++;
        }
        System.out.println("\\n============== HOME SUMMARY ==============");
        System.out.println("Total tasks     : " + tasks.size());
        System.out.println("Completed       : " + completed);
        System.out.println("Pending         : " + pending);
        System.out.println("Overdue         : " + overdue);
        if (!tasks.isEmpty()) {
            int percent = completed * 100 / tasks.size();
            System.out.println("Progress        : " + percent + "%");
            System.out.println("[" + "#".repeat(percent / 5) + "-".repeat(20 - percent / 5) + "]");
        }
        System.out.println("==========================================");
    }

    private static Maintenance findTask(int id) {
        for (Maintenance task : tasks) if (task.getId() == id) return task;
        return null;
    }

    private static void printHeader() {
        System.out.printf("%-5s | %-24s | %-14s | %-12s | %-8s | %s%n",
            "ID", "TASK", "AREA", "DUE DATE", "PRIORITY", "STATUS");
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException ex) {
                System.out.println("Use the date format YYYY-MM-DD, for example 2026-12-15.");
            }
        }
    }

    private static String readPriority() {
        while (true) {
            System.out.print("Priority (Low / Medium / High): ");
            String priority = scanner.nextLine().trim();
            if (priority.equalsIgnoreCase("low")) return "Low";
            if (priority.equalsIgnoreCase("medium")) return "Medium";
            if (priority.equalsIgnoreCase("high")) return "High";
            System.out.println("Please enter Low, Medium, or High.");
        }
    }

    private static void addSampleTasks() {
        tasks.add(new Maintenance(nextId++, "Replace air filter", "HVAC",
            LocalDate.now().plusDays(3), "Medium", "Check the filter size first."));
        tasks.add(new Maintenance(nextId++, "Inspect smoke alarms", "Bedrooms",
            LocalDate.now().plusDays(7), "High", "Test every alarm."));
        tasks.add(new Maintenance(nextId++, "Clean gutters", "Roof",
            LocalDate.now().minusDays(2), "High", "Remove leaves and debris."));
    }
}
