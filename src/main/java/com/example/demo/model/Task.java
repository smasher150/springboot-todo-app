package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    // New features
    private String priority; // HIGH, MEDIUM, LOW
    private List<String> tags;
    private List<Subtask> subtasks;
    private String recurringPattern; // DAILY, WEEKLY, MONTHLY
    private LocalDate nextDueDate;
    private Long parentTaskId; // For subtasks
    private boolean isMilestone;
    private List<Long> dependencies;
    private int estimatedMinutes; // For Pomodoro timer

    public Task() {
        this.createdAt = LocalDateTime.now();
        this.tags = new ArrayList<>();
        this.subtasks = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.priority = "MEDIUM";
        this.estimatedMinutes = 25;
    }

    public Task(Long id, String description, boolean completed, LocalDate dueDate, String category) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.tags = new ArrayList<>();
        this.subtasks = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.priority = "MEDIUM";
        this.estimatedMinutes = 25;
    }

    // Getters and Setters for existing fields
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

    // New feature getters and setters
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public String getRecurringPattern() {
        return recurringPattern;
    }

    public void setRecurringPattern(String recurringPattern) {
        this.recurringPattern = recurringPattern;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public boolean isMilestone() {
        return isMilestone;
    }

    public void setMilestone(boolean milestone) {
        isMilestone = milestone;
    }

    public List<Long> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Long> dependencies) {
        this.dependencies = dependencies;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    // Helper methods
    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public int getCompletedSubtasksCount() {
        return (int) subtasks.stream().filter(Subtask::isCompleted).count();
    }

    public int getTotalSubtasksCount() {
        return subtasks.size();
    }

    public double getSubtaskProgress() {
        if (subtasks.isEmpty()) return 0;
        return (double) getCompletedSubtasksCount() / getTotalSubtasksCount() * 100;
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
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", subtasks=" + subtasks +
                ", recurringPattern='" + recurringPattern + '\'' +
                ", nextDueDate=" + nextDueDate +
                ", parentTaskId=" + parentTaskId +
                ", isMilestone=" + isMilestone +
                ", dependencies=" + dependencies +
                ", estimatedMinutes=" + estimatedMinutes +
                '}';
    }
}
