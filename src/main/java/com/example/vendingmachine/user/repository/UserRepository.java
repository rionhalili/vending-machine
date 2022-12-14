package com.example.vendingmachine.user.repository;

import com.example.vendingmachine.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);

    User findUserById(UUID id);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
}
