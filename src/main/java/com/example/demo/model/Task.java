package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private String category;
    private LocalDateTime createdAt;
    private long daysOld;
    private LocalDateTime reminderTime;
    private String notes;

    public Task() {
        this.createdAt = LocalDateTime.now();
    }

    public Task(Long id, String description, boolean completed, LocalDate dueDate, String category) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getDaysOld() {
        return daysOld;
    }

    public void setDaysOld(long daysOld) {
        this.daysOld = daysOld;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", daysOld=" + daysOld +
                ", reminderTime=" + reminderTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}
