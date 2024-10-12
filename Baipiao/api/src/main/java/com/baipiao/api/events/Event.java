package com.baipiao.api.events;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baipiao.api.categories.Category;
import com.baipiao.api.organizations.Organization;
import com.baipiao.api.tickets.Ticket;
import com.baipiao.api.venues.Venue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")

public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Matches SQL SERIAL
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)  // Matches SQL definition of VARCHAR(100)
    private String name;

    private String details;

    private boolean registrationRequired;

    private String registrationLink;

    private String contactEmail;

    private String contactPhoneNumber;

    private String status;

    private int capacity;

    private String image;

    private LocalDateTime registrationDeadline;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    // Relationships to other entities (Venue, Category, Organization)
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organization organizer;

    @ToString.Exclude  // This will exclude the event reference from the toString() method
    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();

    public Event(String name, String details, boolean registrationRequired, String registrationLink, String contactEmail,
                 String contactPhoneNumber, String status, int capacity, String image, LocalDateTime registrationDeadline,
                 LocalDateTime startDate, LocalDateTime endDate, Venue venue, Category category, Organization organizer) {
        this.name = name;
        this.details = details;
        this.registrationRequired = registrationRequired;
        this.registrationLink = registrationLink;
        this.contactEmail = contactEmail;
        this.contactPhoneNumber = contactPhoneNumber;
        this.status = status;
        this.capacity = capacity;
        this.image = image;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.category = category;
        this.organizer = organizer;
    }
}