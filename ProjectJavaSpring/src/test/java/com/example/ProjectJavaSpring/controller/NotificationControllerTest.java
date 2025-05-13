package com.example.ProjectJavaSpring.controller;

import com.example.ProjectJavaSpring.models.Notification;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.service.NotificationService;
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

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotifications_ShouldReturnList() {
        // Arrange
        Notification notification1 = new Notification("Test 1", false, null, 1L);
        Notification notification2 = new Notification("Test 2", true, null, 2L);
        when(notificationService.getAllNotifications()).thenReturn(Arrays.asList(notification1, notification2));

        // Act
        List<Notification> result = notificationController.getAllNotifications();

        // Assert
        assertEquals(2, result.size());
        verify(notificationService, times(1)).getAllNotifications();
    }

    @Test
    void findById_WhenExists_ShouldReturnNotification() {
        // Arrange
        Notification notification = new Notification("Test", false, null, 1L);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.ok(
                new ResponseObject("ok", "Query notification successfully", notification));
        when(notificationService.findById(1L)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseObject> response = notificationController.findById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody().getStatus());
        verify(notificationService, times(1)).findById(1L);
    }

    @Test
    void insertNotification_ShouldReturnCreatedNotification() {
        // Arrange
        Notification newNotification = new Notification("New", false, null, 1L);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.ok(
                new ResponseObject("ok", "Insert Notification successfully", newNotification));
        when(notificationService.insertNotification(newNotification)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseObject> response = notificationController.insertNotification(newNotification);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody().getStatus());
        verify(notificationService, times(1)).insertNotification(newNotification);
    }
}