package com.baipiao.api.users;

import com.baipiao.api.tickets.Ticket;
import com.baipiao.api.venues.Venue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String userType;

    @Size(max = 100)
    private String displayName;

    @Size(max = 20)
    private String phoneNumber;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ToString.Exclude  // This will exclude the event reference from the toString() method
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();


    @ToString.Exclude  // This will exclude the event reference from the toString() method
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venue> createdVenues = new ArrayList<>();

    @ToString.Exclude  // This will exclude the event reference from the toString() method
    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venue> updatedVenues = new ArrayList<>();

    public User(String email, String userName, String password, String userType, String displayName, String phoneNumber, LocalDateTime createdAt) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }
    // Login Request class
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
    // Register Request class
    @Data
    public static class RegisterRequest {
        @NotNull
        @Size(min = 2, max = 256)
        private String username;

        @NotNull
        @Size(min = 5, max = 256)
        private String email;

        @NotNull
        private String password;

        @NotNull
        @Size(max = 20)
        private String phoneNumber;

        @NotNull
        private  String userType;
    }
    @Data
    public static class UpdatePasswordRequest {
        // Add the user ID
        private Long id;
        private String newPassword;
    }
}
