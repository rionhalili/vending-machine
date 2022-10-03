package com.example.vendingmachine.repository;

import com.example.vendingmachine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
}
