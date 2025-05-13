// TaskController.java
package com.example.ProjectJavaSpring.controller;

import com.example.ProjectJavaSpring.models.Task;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/Tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertTask(@RequestBody Task newTask) {
        return taskService.insertTask(newTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}