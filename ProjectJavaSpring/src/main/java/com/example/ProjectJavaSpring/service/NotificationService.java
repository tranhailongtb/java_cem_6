package com.example.ProjectJavaSpring.service;

import com.example.ProjectJavaSpring.models.Notification;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;

    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    public ResponseEntity<ResponseObject> findById(Long id) {
        Optional<Notification> foundNotification = repository.findById(id);
        return foundNotification.map(notification -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query notification successfully", notification)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("false", "Cannot find notification with id = " + id, "")
        ));
    }

    public ResponseEntity<ResponseObject> insertNotification(Notification newNotification) {
        newNotification.setSentAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Notification successfully", repository.save(newNotification))
        );
    }

    public ResponseEntity<ResponseObject> markAsRead(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setRead(true);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Notification marked as read", repository.save(notification))
                    );
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("false", "Cannot find notification with id = " + id, "")
                ));
    }

    public ResponseEntity<ResponseObject> deleteNotification(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete notification successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find notification to delete", "")
        );
    }

    public ResponseEntity<ResponseObject> findByUserId(Long userId) {
        List<Notification> notifications = repository.findByUserId(userId);
        if (notifications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "No notifications found for user id = " + userId, "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Found notifications", notifications)
        );
    }
}