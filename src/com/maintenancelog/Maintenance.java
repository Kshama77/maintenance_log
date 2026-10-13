package com.maintenancelog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents one home or property maintenance task.
 */
public class Maintenance {
    private final int id;
    private String title;
    private String area;
    private LocalDate dueDate;
    private String priority;
    private boolean completed;
    private String notes;

    public Maintenance(int id, String title, String area, LocalDate dueDate,
                       String priority, String notes) {
        this.id = id;
        this.title = title;
        this.area = area;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.completed = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArea() { return area; }
    public LocalDate getDueDate() { return dueDate; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public String getNotes() { return notes; }

    public void setTitle(String title) { this.title = title; }
    public void setArea(String area) { this.area = area; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setNotes(String notes) { this.notes = notes; }
    public void markCompleted() { completed = true; }

    public String getStatus() {
        if (completed) return "COMPLETED";
        if (dueDate.isBefore(LocalDate.now())) return "OVERDUE";
        if (dueDate.equals(LocalDate.now())) return "DUE TODAY";
        return "UPCOMING";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return String.format(
            "#%-3d | %-24s | %-14s | %-12s | %-8s | %s",
            id, shorten(title, 24), shorten(area, 14), dueDate.format(formatter),
            priority, getStatus()
        );
    }

    private String shorten(String value, int length) {
        if (value == null) return "";
        return value.length() <= length ? value : value.substring(0, length - 3) + "...";
    }
}
