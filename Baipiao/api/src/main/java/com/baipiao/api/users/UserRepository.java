package com.baipiao.api.users;

import java.util.List;
import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Read: Retrieve all users
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAllUsers();

    // Read: Retrieve a user by its ID
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User findUserById(@Param("id") Long id);

    // Read: Retrieve a user by its name
    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User findUserByUserName(@Param("username") String userName);

    // Create: Insert a new user (not typically done in a repository, but here's a
    // native query version)
    // Create: Insert a new user
    @Modifying
    @Transactional

    @Query(value = "INSERT INTO users (email, username, password, phone_number, display_name, user_type, created_at) VALUES (:email, :username, :password, :phone_number, :display_name, :user_type, :created_at)", nativeQuery = true)
    void insertUser(@Param("email") String name, @Param("username") String description, @Param("password") String password, @Param("phone_number") String phone_number, @Param("display_name") String displayName,  @Param("user_type") String userType, @Param("created_at") LocalDateTime createdAt);

    // Update: Update a user by its ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET email = :email, username = :username , display_name = :display_name, password = :password, phone_number = :phone_number, user_type= :user_type, created_at = :created_at WHERE id = :id", nativeQuery = true)
    void updateUser(@Param("id") Long id, @Param("email") String name, @Param("username") String description, @Param("password") String password, @Param("phone_number") String phone_number, @Param("display_name") String displayName,  @Param("user_type") String userType, @Param("created_at") LocalDateTime createdAt);

    // Delete: Delete a user by its ID
    @Modifying
    @Transactional
    // @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    void deleteUserById(@Param("id") Long id);

}