package com.baipiao.api.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAll();

    // Example for finding a user by email
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    // Example for finding users by role
    @Query(value = "SELECT * FROM users WHERE role = :role", nativeQuery = true)
    List<User> findByRole(@Param("role") String role);

    // Example for finding active users
    @Query(value = "SELECT * FROM users WHERE is_active = true", nativeQuery = true)
    List<User> findActiveUsers();
}