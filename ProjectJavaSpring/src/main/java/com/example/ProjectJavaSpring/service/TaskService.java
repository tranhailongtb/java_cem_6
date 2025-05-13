package com.example.ProjectJavaSpring.service;

import com.example.ProjectJavaSpring.models.Task;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    @Cacheable(value = "tasks", unless = "#result == null")
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Cacheable(value = "task", key = "#id", unless = "#result.getBody().getData() == null")
    public ResponseEntity<ResponseObject> findById(Long id) {
        Optional<Task> foundTask = repository.findById(id);
        return foundTask.map(task -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query task successfully", task)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("false", "Cannot find task with id = " + id, "")
        ));
    }

    @CacheEvict(value = {"tasks", "task"}, allEntries = true)
    public ResponseEntity<ResponseObject> insertTask(Task newTask) {
        newTask.setCreatedAt(LocalDateTime.now());
        newTask.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Task successfully", repository.save(newTask))
        );
    }

    @CacheEvict(value = {"tasks", "task"}, allEntries = true)
    public ResponseEntity<ResponseObject> deleteTask(Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete task successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find task to delete", "")
        );
    }
}