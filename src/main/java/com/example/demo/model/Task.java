package com.example.demo.model;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String description;
    private boolean completed;
    private LocalDate dueDate;

    public Task() {
    }

    public Task(Long id, String description, boolean completed, LocalDate dueDate) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                '}';
    }
}
