package com.example.demo.model;

public class TaskTemplate {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Integer daysToDue;

    public TaskTemplate() {}

    public TaskTemplate(String name, String description, String category, Integer daysToDue) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.daysToDue = daysToDue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDaysToDue() {
        return daysToDue;
    }

    public void setDaysToDue(Integer daysToDue) {
        this.daysToDue = daysToDue;
    }

    @Override
    public String toString() {
        return "TaskTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", daysToDue=" + daysToDue +
                '}';
    }
}
