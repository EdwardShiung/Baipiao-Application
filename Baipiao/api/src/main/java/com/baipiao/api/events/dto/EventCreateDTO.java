package com.baipiao.api.events.dto;

import java.time.LocalDateTime;

import com.baipiao.api.config.CustomLocalDateTimeDeserializer;
import com.baipiao.api.config.CustomLocalDateTimeSerializer;
import com.baipiao.api.events.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private Long venueId;
    private Long categoryId;
    private Long organizerId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime registrationDeadline;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
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
        this.venueId = event.getVenue().getId();
        this.categoryId = event.getCategory().getId();
        this.organizerId = event.getOrganizer().getId();
        this.registrationDeadline = event.getRegistrationDeadline();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }
    public Event toEvent() {
        Event event = new Event();
        event.setName(name);
        event.setDetails(details);
        event.setRegistrationRequired(registrationRequired);
        event.setRegistrationLink(registrationLink);
        event.setContactEmail(contactEmail);
        event.setContactPhoneNumber(contactPhoneNumber);
        event.setStatus(status);
        event.setCapacity(capacity);
        event.setImage(image);
        event.setRegistrationDeadline(registrationDeadline);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        return event;
    }
}