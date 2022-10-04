package com.example.vendingmachine.user.service;

import com.example.vendingmachine.user.dto.UserDTO;
import com.example.vendingmachine.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getUser(UUID id);

    Optional<User> findUserByUsername(String username);

    void delete(User user);

//    User saveUser(UserDTO userDTO);

    Optional<User> updateUser(UUID id, UserDTO userDTO);
}