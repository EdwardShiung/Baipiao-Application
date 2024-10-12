package com.baipiao.api.events.dtos;

import java.time.LocalDateTime;

import com.baipiao.api.events.Event;

import lombok.Data;
@Data
public class EventCreateDTO{
    private Long id;
    private String name;
    private String details;
    private boolean registrationRequired;
    private String registrationLink;
    private String contactEmail;
    private String contactPhoneNumber;
    private String status;
    private int capacity;
    private String image;
    private Long venue;
    private Long category;
    private Long organizer;

    private LocalDateTime registrationDeadline;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public EventCreateDTO() {
        
    }
    public EventCreateDTO(Event event) {
        this.name = event.getName();
        this.details = event.getDetails();
        this.registrationRequired = event.isRegistrationRequired();
        this.registrationLink = event.getRegistrationLink();
        this.contactEmail = event.getContactEmail();
        this.contactPhoneNumber = event.getContactPhoneNumber();
        this.status = event.getStatus();
        this.capacity = event.getCapacity();
        this.image = event.getImage();
        this.venue = event.getVenue().getId();
        this.category = event.getCategory().getId();
        this.organizer = event.getOrganizer().getId();
        this.registrationDeadline = event.getRegistrationDeadline();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }
    
}