package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void statusEndpoint() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Application is running successfully!"));
    }

    @Test
    void homePageLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/html"));
    }

    @Test
    void addTask() throws Exception {
        mockMvc.perform(post("/addTask")
                .param("task", "Test Task")
                .param("dueDate", ""))
                .andExpect(status().isOk());
    }

    @Test
    void toggleTask() throws Exception {
        // First add a task
        mockMvc.perform(post("/addTask")
                .param("task", "Test Task")
                .param("dueDate", ""))
                .andExpect(status().isOk());

        // Then toggle it
        mockMvc.perform(post("/toggleTask")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTask() throws Exception {
        // First add a task
        mockMvc.perform(post("/addTask")
                .param("task", "Test Task")
                .param("dueDate", ""))
                .andExpect(status().isOk());

        // Then delete it
        mockMvc.perform(post("/deleteTask")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void editTask() throws Exception {
        // First add a task
        mockMvc.perform(post("/addTask")
                .param("task", "Original Task")
                .param("dueDate", ""))
                .andExpect(status().isOk());

        // Then edit it
        mockMvc.perform(post("/editTask")
                .param("id", "1")
                .param("description", "Updated Task")
                .param("dueDate", ""))
                .andExpect(status().isOk());
    }
}
