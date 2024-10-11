package com.baipiao.api.users;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")  // Changed to "users" to avoid case-sensitive issues
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use IDENTITY to match SQL SERIAL behavior
    private Long id;

    @NotNull
    @Size(min = 2, max = 256)
    @Column(unique = true)  // Email should be unique
    private String email;

    @NotNull
    @Size(min = 2, max = 256)
    @Column(name = "username", unique = true)  // Ensure userName is unique and mapped correctly
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String role;

    private String profilePicture;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 100)
    private String displayName;

    @Size(max = 20)
    private String phone;

    @Size(max = 500)
    private String bio;

    private String website;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @UpdateTimestamp
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    // Constructor for creating a new user
    public User(String email, String userName, String password, String role, String firstName, String lastName) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.isActive = true;  // Defaulting to active when created
        this.isVerified = false;  // Defaulting to not verified
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = firstName + " " + lastName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}