package com.example.ProjectJavaSpring.controller;

import com.example.ProjectJavaSpring.models.Task;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        // Arrange
        Task task1 = new Task("Task 1", "Desc 1", false, null, null);
        Task task2 = new Task("Task 2", "Desc 2", true, null, null);
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> result = taskController.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void findById_WhenExists_ShouldReturnTask() {
        // Arrange
        Task task = new Task("Task", "Desc", false, null, null);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.ok(
                new ResponseObject("ok", "Query task successfully", task));
        when(taskService.findById(1L)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseObject> response = taskController.findById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody().getStatus());
        verify(taskService, times(1)).findById(1L);
    }

    @Test
    void insertTask_ShouldReturnCreatedTask() {
        // Arrange
        Task newTask = new Task("New Task", "New Desc", false, null, null);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.ok(
                new ResponseObject("ok", "Insert Task successfully", newTask));
        when(taskService.insertTask(newTask)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseObject> response = taskController.insertTask(newTask);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody().getStatus());
        verify(taskService, times(1)).insertTask(newTask);
    }
}