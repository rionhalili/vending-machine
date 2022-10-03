package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.UserDTO;
import com.example.vendingmachine.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getUser(UUID id);

    Optional<User> findUserByUsername(String username);

    void delete(User user);

    User saveUser(UserDTO userDTO);

    User updateUser(UUID id, UserDTO userDTO);
}
