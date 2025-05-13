package com.example.ProjectJavaSpring.controller;

import com.example.ProjectJavaSpring.models.Notification;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/Notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        return notificationService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> findByUserId(@PathVariable Long userId) {
        return notificationService.findByUserId(userId);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertNotification(@RequestBody Notification newNotification) {
        return notificationService.insertNotification(newNotification);
    }

    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<ResponseObject> markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteNotification(@PathVariable Long id) {
        return notificationService.deleteNotification(id);
    }
}