package com.example.ProjectJavaSpring.controller;

import com.example.ProjectJavaSpring.models.User;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.service.UserService;
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

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnList() {
        // Arrange
        User user1 = new User("user1", "user1@test.com", "hash1", null);
        User user2 = new User("user2", "user2@test.com", "hash2", null);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> result = userController.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_WhenExists_ShouldReturnUser() {
        // Arrange
        User user = new User("user", "user@test.com", "hash", null);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.ok(
                new ResponseObject("ok", "Query user successfully", user));
        when(userService.getUserById(1L)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseObject> response = userController.getUserById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody().getStatus());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        User newUser = new User("new", "new@test.com", "newhash", null);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.ok(
                new ResponseObject("ok", "Insert user successfully", newUser));
        when(userService.createUser(newUser)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseObject> response = userController.createUser(newUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody().getStatus());
        verify(userService, times(1)).createUser(newUser);
    }
}