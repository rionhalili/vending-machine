package com.example.vendingmachine.user.service;

import com.example.vendingmachine.user.dto.DepositDTO;
import com.example.vendingmachine.user.dto.UserRequest;
import com.example.vendingmachine.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getUser(UUID id);

    Optional<User> findUserByUsername(String username);

    void delete(User user);

    Optional<User> updateUser(UUID id, UserRequest userRequest);

    User deposit(User user, DepositDTO depositDTO);

    User updateBuyerDeposit(User user, double change);

    User updateSellerDeposit(User user, double deposit);

    User reset(User user);
}
