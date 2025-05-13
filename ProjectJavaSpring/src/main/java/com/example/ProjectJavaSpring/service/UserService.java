package com.example.ProjectJavaSpring.service;

import com.example.ProjectJavaSpring.models.User;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public ResponseEntity<ResponseObject> getUserById(Long id) {
        Optional<User> foundUser = repository.findById(id);
        return foundUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query user successfully", user)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("false", "Cannot find user with id = " + id, "")
        ));
    }

    public ResponseEntity<ResponseObject> createUser(User newUser) {
        if (repository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Email already exists", "")
            );
        }

        newUser.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert user successfully", repository.save(newUser))
        );
    }
}