package com.example.demo.model;

import java.time.LocalDateTime;

public class TaskHistory {
    private Long id;
    private Long taskId;
    private String action;
    private String oldValue;
    private String newValue;
    private LocalDateTime timestamp;
    private String description;

    public TaskHistory() {
        this.timestamp = LocalDateTime.now();
    }

    public TaskHistory(Long taskId, String action, String oldValue, String newValue, String description) {
        this.taskId = taskId;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskHistory{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", action='" + action + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }
}
