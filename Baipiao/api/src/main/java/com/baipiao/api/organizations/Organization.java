package com.baipiao.api.organizations;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.baipiao.api.events.Event;
import com.baipiao.api.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // To match SQL SERIAL behavior
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)  // Matches SQL VARCHAR(100)
    private String name;

    private String description;

    @Size(max = 100)
    private String website;

    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phoneno;

    private String address;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizer")
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor
    public Organization(String name, String description, String website, String email, String phoneno, String address) {
        this.name = name;
        this.description = description;
        this.website = website;
        this.email = email;
        this.phoneno = phoneno;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}