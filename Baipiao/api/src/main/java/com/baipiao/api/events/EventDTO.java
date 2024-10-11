package com.baipiao.api.events;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class EventDTO{
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
    private String venue;
    private String category;
    private String organizer;

    private LocalDateTime registrationDeadline;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.details = event.getDetails();
        this.registrationRequired = event.isRegistrationRequired();
        this.registrationLink = event.getRegistrationLink();
        this.contactEmail = event.getContactEmail();
        this.contactPhoneNumber = event.getContactPhoneNumber();
        this.status = event.getStatus();
        this.capacity = event.getCapacity();
        this.image = event.getImage();
        this.venue = event.getVenue().getName();
        this.category = event.getCategory().getName();
        this.organizer = event.getOrganizer().getName();
        this.registrationDeadline = event.getRegistrationDeadline();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }
    
}