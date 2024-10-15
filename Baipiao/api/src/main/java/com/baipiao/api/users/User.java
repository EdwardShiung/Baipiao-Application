package com.baipiao.api.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baipiao.api.events.Event;
import com.baipiao.api.tickets.Ticket;
import com.baipiao.api.venues.Venue;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.ToString;

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



}