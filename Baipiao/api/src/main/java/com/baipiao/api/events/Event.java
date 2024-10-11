package com.baipiao.api.events;

import java.io.Serializable;

import com.baipiao.api.categories.Category;
import com.baipiao.api.organizations.Organization;
import com.baipiao.api.venues.Venue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
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

    // Constructor for creating an Event
    public Event(String name, boolean registrationRequired, String details, String contactEmail, 
                 String contactPhoneNumber, String status,  int capacity, String registrationLink, 
                 String image, Organization organizer, Venue venue) {
        this.name = name;
        this.registrationRequired = registrationRequired;
        this.details = details;
        this.contactEmail = contactEmail;
        this.contactPhoneNumber = contactPhoneNumber;
        this.status = status;
        this.capacity = capacity;
        this.registrationLink = registrationLink;
        this.image = image;
        this.organizer = organizer;
        this.venue = venue;
    }
}