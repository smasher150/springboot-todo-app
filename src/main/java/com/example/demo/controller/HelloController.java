package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Task;

@Controller

public class HelloController {

    // simple in-memory task store with ID generation
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasks);
        return "home";   
    }

    // new endpoint to add a todo task
    @GetMapping("/addTask")
    public String addTaskRedirect() {
        return "redirect:/";
    }
    
    @PostMapping("/addTask")
    public String addTask(@RequestParam("task") String description, 
                         @RequestParam(value = "dueDate", required = false) String dueDate, Model model) {
        LocalDate parsedDueDate = null;
        if (dueDate != null && !dueDate.isEmpty()) {
            parsedDueDate = LocalDate.parse(dueDate);
            // Validate that due date is not in the past
            if (parsedDueDate.isBefore(LocalDate.now())) {
                model.addAttribute("tasks", tasks);
                model.addAttribute("message", "Error: Due date cannot be in the past!");
                return "home";
            }
        }
        
        Task newTask = new Task(idCounter.getAndIncrement(), description, false, parsedDueDate);
        tasks.add(newTask);
        model.addAttribute("tasks", tasks);
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
                task.setCompleted(!task.isCompleted());
                model.addAttribute("message", "Task " + (task.isCompleted() ? "completed" : "marked as incomplete"));
                break;
            }
        }
        model.addAttribute("tasks", tasks);
        return "home";
    }
    
    // delete task
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("id") Long id, Model model) {
        tasks.removeIf(task -> task.getId().equals(id));
        model.addAttribute("tasks", tasks);
        model.addAttribute("message", "Task deleted successfully");
        return "home";
    }
    
    // edit task form
    @PostMapping("/editTask")
    public String editTask(@RequestParam("id") Long id, 
                          @RequestParam("description") String description,
                          @RequestParam(value = "dueDate", required = false) String dueDate, Model model) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setDescription(description);
                if (dueDate != null && !dueDate.isEmpty()) {
                    LocalDate parsedDueDate = LocalDate.parse(dueDate);
                    // Validate that due date is not in the past
                    if (parsedDueDate.isBefore(LocalDate.now())) {
                        model.addAttribute("tasks", tasks);
                        model.addAttribute("message", "Error: Due date cannot be in the past!");
                        return "home";
                    }
                    task.setDueDate(parsedDueDate);
                }
                model.addAttribute("message", "Task updated successfully");
                break;
            }
        }
        model.addAttribute("tasks", tasks);
        return "home";
    }
}