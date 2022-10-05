package com.example.vendingmachine.user.controller;

import com.example.vendingmachine.security.services.UserDetailsServiceImpl;
import com.example.vendingmachine.user.dto.DepositDTO;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class DepositController {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    public DepositController(UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PutMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity<?> deposit(@RequestBody DepositDTO depositDTO) {
        if(!depositDTO.validate().isEmpty()) {
            return new ResponseEntity<>(Map.of("message", depositDTO.validate()), HttpStatus.BAD_REQUEST);
        }
        String currentUser = userDetailsService.getCurrentUser();
        Optional<User> user = userService.findUserByUsername(currentUser);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.deposit(user.get(), depositDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity<?> reset() {
        String currentUser = userDetailsService.getCurrentUser();
        Optional<User> user = userService.findUserByUsername(currentUser);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.reset(user.get()), HttpStatus.OK);
    }
}
