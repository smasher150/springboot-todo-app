package com.example.demo.model;

import java.time.LocalDateTime;

public class Subtask {
    private Long id;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Long parentTaskId;

    public Subtask() {
        this.createdAt = LocalDateTime.now();
    }

    public Subtask(Long id, String description, Long parentTaskId) {
        this.id = id;
        this.description = description;
        this.parentTaskId = parentTaskId;
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    public Subtask(Long id, String description, boolean completed, Long parentTaskId) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.parentTaskId = parentTaskId;
        this.createdAt = LocalDateTime.now();
        if (completed) {
            this.completedAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
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
        if (completed && completedAt == null) {
            this.completedAt = LocalDateTime.now();
        } else if (!completed) {
            this.completedAt = null;
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", completedAt=" + completedAt +
                ", parentTaskId=" + parentTaskId +
                '}';
    }
}
