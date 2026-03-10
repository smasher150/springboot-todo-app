package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Task;
import com.example.demo.model.TaskHistory;
import com.example.demo.model.TaskTemplate;

@Controller
public class HelloController {

    // simple in-memory task store with ID generation
    private final List<Task> tasks = new ArrayList<>();
    private final List<TaskHistory> taskHistory = new ArrayList<>();
    private final List<TaskTemplate> taskTemplates = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final AtomicLong historyIdCounter = new AtomicLong(1);
    private final AtomicLong templateIdCounter = new AtomicLong(1);

    // Initialize default templates
    public HelloController() {
        initializeDefaultTemplates();
    }

    private void initializeDefaultTemplates() {
        TaskTemplate template1 = new TaskTemplate(
            "Meeting",
            "Attend meeting with team",
            "Work",
            1
        );
        template1.setId(templateIdCounter.getAndIncrement());
        taskTemplates.add(template1);
        
        TaskTemplate template2 = new TaskTemplate(
            "Code Review",
            "Review pull requests",
            "Work",
            2
        );
        template2.setId(templateIdCounter.getAndIncrement());
        taskTemplates.add(template2);
        
        TaskTemplate template3 = new TaskTemplate(
            "Documentation",
            "Update project documentation",
            "Work",
            7
        );
        template3.setId(templateIdCounter.getAndIncrement());
        taskTemplates.add(template3);
    }

    // helper method to add history entry
    private void addHistory(Long taskId, String action, String oldValue, String newValue, String description) {
        TaskHistory history = new TaskHistory(taskId, action, oldValue, newValue, description);
        history.setId(historyIdCounter.getAndIncrement());
        taskHistory.add(history);
    }

    // helper method to update model attributes used by home
    private void updateModel(Model model) {
        model.addAttribute("tasks", tasks);
        model.addAttribute("totalCount", tasks.size());
        model.addAttribute("completedCount", tasks.stream().filter(Task::isCompleted).count());
        model.addAttribute("categories", tasks.stream().map(Task::getCategory).filter(c -> c != null && !c.isEmpty()).distinct().collect(Collectors.toList()));
        
        // Calculate completion percentage
        long total = tasks.size();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        double percentage = total > 0 ? (double) completed / total * 100 : 0;
        model.addAttribute("completionPercentage", String.format("%.1f", percentage));
        
        // Templates
        model.addAttribute("templates", taskTemplates);
        
        // Reminders
        long reminderCount = tasks.stream().filter(t -> t.getReminderTime() != null && !t.isCompleted()).count();
        model.addAttribute("reminderCount", reminderCount);
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "filter", required = false) String filter,
                      @RequestParam(value = "category", required = false) String category,
                      @RequestParam(value = "search", required = false) String search,
                      @RequestParam(value = "sortBy", required = false) String sortBy,
                      @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
                      Model model) {
        
        List<Task> filteredTasks = new ArrayList<>(tasks);
        
        // Apply search filter
        if (search != null && !search.trim().isEmpty()) {
            final String searchTerm = search.trim().toLowerCase();
            filteredTasks = filteredTasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(searchTerm) ||
                               (task.getCategory() != null && task.getCategory().toLowerCase().contains(searchTerm)) ||
                               (task.getNotes() != null && task.getNotes().toLowerCase().contains(searchTerm)))
                .collect(Collectors.toList());
        }
        
        // Apply status filter
        if ("completed".equals(filter)) {
            filteredTasks = filteredTasks.stream().filter(Task::isCompleted).collect(Collectors.toList());
        } else if ("active".equals(filter)) {
            filteredTasks = filteredTasks.stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
        }
        
        // Apply category filter
        if (category != null && !category.isEmpty()) {
            filteredTasks = filteredTasks.stream().filter(t -> category.equals(t.getCategory())).collect(Collectors.toList());
        }
        
        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Task> comparator = null;
            boolean ascending = "asc".equals(sortOrder);
            
            switch (sortBy) {
                case "description":
                    comparator = Comparator.comparing(Task::getDescription, (a, b) -> a.compareToIgnoreCase(b));
                    break;
                case "dueDate":
                    comparator = Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(Task::getCreatedAt);
                    break;
                case "category":
                    comparator = Comparator.comparing(Task::getCategory, Comparator.nullsLast((a, b) -> a.compareToIgnoreCase(b)));
                    break;
                case "status":
                    comparator = Comparator.comparing(Task::isCompleted);
                    break;
                case "age":
                    comparator = Comparator.comparing(Task::getCreatedAt);
                    break;
            }
            
            if (comparator != null) {
                filteredTasks.sort(ascending ? comparator : comparator.reversed());
            }
        }
        
        // Add task aging information
        filteredTasks.forEach(task -> {
            long daysOld = ChronoUnit.DAYS.between(task.getCreatedAt(), LocalDateTime.now());
            task.setDaysOld(daysOld);
        });
        
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("totalCount", tasks.size());
        model.addAttribute("completedCount", tasks.stream().filter(Task::isCompleted).count());
        model.addAttribute("categories", tasks.stream().map(Task::getCategory).filter(c -> c != null && !c.isEmpty()).distinct().collect(Collectors.toList()));
        model.addAttribute("currentFilter", filter);
        model.addAttribute("currentCategory", category);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentSortBy", sortBy);
        model.addAttribute("currentSortOrder", sortOrder);
        
        // Calculate completion percentage
        long total = tasks.size();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        double percentage = total > 0 ? (double) completed / total * 100 : 0;
        model.addAttribute("completionPercentage", String.format("%.1f", percentage));
        
        // Templates
        model.addAttribute("templates", taskTemplates);
        
        // Reminders
        long reminderCount = tasks.stream().filter(t -> t.getReminderTime() != null && !t.isCompleted()).count();
        model.addAttribute("reminderCount", reminderCount);
        
        return "home";   
    }

    @GetMapping("/addTask")
    public String addTaskRedirect() {
        return "redirect:/";
    }
    
    @PostMapping("/addTask")
    public String addTask(@RequestParam("task") String description, 
                         @RequestParam(value = "dueDate", required = false) String dueDate,
                         @RequestParam(value = "category", required = false) String category,
                         @RequestParam(value = "reminderTime", required = false) String reminderTime,
                         @RequestParam(value = "notes", required = false) String notes,
                         Model model) {
        LocalDate parsedDueDate = null;
        if (dueDate != null && !dueDate.isEmpty()) {
            parsedDueDate = LocalDate.parse(dueDate);
            // Validate that due date is not in past
            if (parsedDueDate.isBefore(LocalDate.now())) {
                updateModel(model);
                model.addAttribute("message", "Error: Due date cannot be in the past!");
                return "home";
            }
        }
        
        LocalDateTime parsedReminderTime = null;
        if (reminderTime != null && !reminderTime.isEmpty()) {
            parsedReminderTime = LocalDateTime.parse(reminderTime);
        }
        
        Task newTask = new Task(idCounter.getAndIncrement(), description, false, parsedDueDate, category);
        newTask.setReminderTime(parsedReminderTime);
        newTask.setNotes(notes);
        tasks.add(newTask);
        
        addHistory(newTask.getId(), "CREATED", null, description, "Task created");
        
        updateModel(model);
        model.addAttribute("message", "Task added: " + description);
        return "home";
    }

    @GetMapping("/api/status")
    @ResponseBody
    public String status() {
        return "Application is running successfully!";
    }
    
    // mark task as complete/incomplete
    @PostMapping("/toggleTask")
    public String toggleTask(@RequestParam("id") Long id, Model model) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                boolean wasCompleted = task.isCompleted();
                task.setCompleted(!task.isCompleted());
                addHistory(task.getId(), "TOGGLED", wasCompleted ? "COMPLETED" : "ACTIVE", 
                          task.isCompleted() ? "COMPLETED" : "ACTIVE", 
                          "Task status changed");
                model.addAttribute("message", "Task " + (task.isCompleted() ? "completed" : "marked as incomplete"));
                break;
            }
        }
        updateModel(model);
        return "home";
    }
    
    // delete task
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("id") Long id, Model model) {
        Task deletedTask = null;
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                deletedTask = task;
                break;
            }
        }
        if (deletedTask != null) {
            addHistory(deletedTask.getId(), "DELETED", deletedTask.getDescription(), null, "Task deleted");
            tasks.removeIf(task -> task.getId().equals(id));
            updateModel(model);
            model.addAttribute("message", "Task deleted successfully");
        }
        return "home";
    }
    
    // edit task form
    @PostMapping("/editTask")
    public String editTask(@RequestParam("id") Long id, 
                          @RequestParam("description") String description,
                          @RequestParam(value = "dueDate", required = false) String dueDate,
                          @RequestParam(value = "category", required = false) String category,
                          @RequestParam(value = "reminderTime", required = false) String reminderTime,
                          @RequestParam(value = "notes", required = false) String notes,
                          Model model) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                String oldDescription = task.getDescription();
                task.setDescription(description);
                
                if (dueDate != null && !dueDate.isEmpty()) {
                    LocalDate parsedDueDate = LocalDate.parse(dueDate);
                    // Validate that due date is not in past
                    if (parsedDueDate.isBefore(LocalDate.now())) {
                        updateModel(model);
                        model.addAttribute("message", "Error: Due date cannot be in the past!");
                        return "home";
                    }
                    task.setDueDate(parsedDueDate);
                }
                
                task.setCategory(category);
                
                if (reminderTime != null && !reminderTime.isEmpty()) {
                    task.setReminderTime(LocalDateTime.parse(reminderTime));
                }
                
                task.setNotes(notes);
                
                addHistory(task.getId(), "EDITED", oldDescription, description, "Task updated");
                model.addAttribute("message", "Task updated successfully");
                break;
            }
        }
        updateModel(model);
        return "home";
    }

    // duplicate task
    @PostMapping("/duplicateTask")
    public String duplicateTask(@RequestParam("id") Long id, Model model) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                Task duplicatedTask = new Task(
                    idCounter.getAndIncrement(),
                    task.getDescription() + " (Copy)",
                    false,
                    task.getDueDate(),
                    task.getCategory()
                );
                duplicatedTask.setReminderTime(task.getReminderTime());
                duplicatedTask.setNotes(task.getNotes());
                tasks.add(duplicatedTask);
                
                addHistory(duplicatedTask.getId(), "DUPLICATED", null, duplicatedTask.getDescription(), 
                          "Task duplicated from #" + task.getId());
                
                updateModel(model);
                model.addAttribute("message", "Task duplicated successfully");
                break;
            }
        }
        return "home";
    }

    // create task from template
    @PostMapping("/createFromTemplate")
    public String createFromTemplate(@RequestParam("templateId") Long templateId, Model model) {
        for (TaskTemplate template : taskTemplates) {
            if (template.getId().equals(templateId)) {
                LocalDate dueDate = null;
                if (template.getDaysToDue() != null && template.getDaysToDue() > 0) {
                    dueDate = LocalDate.now().plusDays(template.getDaysToDue());
                }
                
                Task newTask = new Task(
                    idCounter.getAndIncrement(),
                    template.getDescription(),
                    false,
                    dueDate,
                    template.getCategory()
                );
                tasks.add(newTask);
                
                addHistory(newTask.getId(), "CREATED_FROM_TEMPLATE", null, newTask.getDescription(), 
                          "Created from template: " + template.getName());
                
                updateModel(model);
                model.addAttribute("message", "Task created from template: " + template.getName());
                break;
            }
        }
        return "home";
    }

    // view task history
    @GetMapping("/taskHistory")
    @ResponseBody
    public String getTaskHistory(@RequestParam(value = "taskId", required = false) Long taskId) {
        if (taskHistory.isEmpty()) {
            return "No history found";
        }
        
        StringBuilder history = new StringBuilder();
        history.append("TASK HISTORY\n");
        history.append("============\n");
        history.append("Generated on: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        
        List<TaskHistory> filteredHistory = taskHistory;
        if (taskId != null) {
            filteredHistory = taskHistory.stream()
                .filter(h -> taskId.equals(h.getTaskId()))
                .collect(Collectors.toList());
        }
        
        if (filteredHistory.isEmpty()) {
            return "No history found for task #" + taskId;
        }
        
        // Sort by timestamp (newest first)
        filteredHistory.stream()
            .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
            .forEach(h -> {
                history.append("• ").append(h.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
                history.append("  Task ID: ").append(h.getTaskId()).append("\n");
                history.append("  Action: ").append(h.getAction()).append("\n");
                if (h.getOldValue() != null) {
                    history.append("  Old Value: ").append(h.getOldValue()).append("\n");
                }
                if (h.getNewValue() != null) {
                    history.append("  New Value: ").append(h.getNewValue()).append("\n");
                }
                history.append("  Description: ").append(h.getDescription()).append("\n");
                history.append("\n");
            });
        
        return history.toString();
    }

    // remove all completed tasks
    @PostMapping("/clearCompleted")
    public String clearCompleted(Model model) {
        tasks.removeIf(Task::isCompleted);
        updateModel(model);
        model.addAttribute("message", "Completed tasks cleared");
        return "home";
    }

    // export tasks to text
    @GetMapping("/export")
    @ResponseBody
    public String exportTasks() {
        if (tasks.isEmpty()) {
            return "No tasks to export";
        }
        
        StringBuilder export = new StringBuilder();
        export.append("TODO LIST EXPORT\n");
        export.append("==================\n");
        export.append("Generated on: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        
        export.append("SUMMARY:\n");
        export.append("Total Tasks: ").append(tasks.size()).append("\n");
        export.append("Completed: ").append(tasks.stream().filter(Task::isCompleted).count()).append("\n");
        export.append("Active: ").append(tasks.stream().filter(t -> !t.isCompleted()).count()).append("\n\n");
        
        export.append("TASKS:\n");
        export.append("------\n");
        
        // Sort by creation date
        tasks.stream()
            .sorted(Comparator.comparing(Task::getCreatedAt))
            .forEach(task -> {
                export.append("• ").append(task.getDescription()).append("\n");
                export.append("  Status: ").append(task.isCompleted() ? "COMPLETED" : "ACTIVE").append("\n");
                if (task.getCategory() != null && !task.getCategory().isEmpty()) {
                    export.append("  Category: ").append(task.getCategory()).append("\n");
                }
                if (task.getDueDate() != null) {
                    export.append("  Due Date: ").append(task.getDueDate()).append("\n");
                }
                export.append("  Created: ").append(task.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
                long daysOld = ChronoUnit.DAYS.between(task.getCreatedAt(), LocalDateTime.now());
                export.append("  Age: ").append(daysOld).append(" days\n");
                export.append("\n");
            });
        
        return export.toString();
    }

    // simple JSON API to fetch all tasks
    @GetMapping("/api/tasks")
    @ResponseBody
    public List<Task> getTasks() {
        return tasks;
    }
}
