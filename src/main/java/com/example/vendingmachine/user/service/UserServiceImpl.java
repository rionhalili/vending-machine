package com.example.vendingmachine.user.service;

import com.example.vendingmachine.user.dto.DepositDTO;
import com.example.vendingmachine.user.dto.UserRequest;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Optional<User> updateUser(UUID id, UserRequest userRequest) {
        User user = userRepository.findUserById(id);
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Override
    public User deposit(User user, DepositDTO depositDTO) {
        double total = user.getDeposit() + depositDTO.getDepositPrice();
        user.setDeposit(total);
        return userRepository.save(user);
    }

    @Override
    public User updateBuyerDeposit(User user, double change) {
        user.setDeposit(change);
        return userRepository.save(user);
    }

    @Override
    public User updateSellerDeposit(User user, double deposit) {
        user.setDeposit(user.getDeposit() + deposit);
        return userRepository.save(user);
    }

    @Override
    public User reset(User user) {
        user.setDeposit(0.0);
        return userRepository.save(user);
    }
}
