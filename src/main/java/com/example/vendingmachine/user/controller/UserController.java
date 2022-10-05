package com.example.vendingmachine.user.controller;

import com.example.vendingmachine.user.dto.UserDTO;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable("id") UUID id) {
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, @PathVariable UUID id) {
        if (!userDTO.validate().isEmpty()) {
            return new ResponseEntity<>(Map.of("message", userDTO.validate()), HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.updateUser(id, userDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }
        userService.delete(user.get());
        return new ResponseEntity<>(Map.of("message", "User deleted successfully"), HttpStatus.OK);
    }
}
