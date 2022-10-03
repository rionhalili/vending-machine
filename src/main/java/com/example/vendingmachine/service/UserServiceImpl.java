package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.UserDTO;
import com.example.vendingmachine.model.User;
import com.example.vendingmachine.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User saveUser = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
        return userRepository.save(saveUser);
    }

    @Override
    public User updateUser(UUID id, UserDTO userDTO) {
        User saveUser = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
        saveUser.setId(id);
        return userRepository.save(saveUser);
    }
}
