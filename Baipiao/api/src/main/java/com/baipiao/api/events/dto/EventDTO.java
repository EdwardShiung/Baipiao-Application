package com.baipiao.api.events.dto;

import java.time.LocalDateTime;

import javax.tools.JavaFileManager.Location;

import com.baipiao.api.config.CustomLocalDateTimeDeserializer;
import com.baipiao.api.config.CustomLocalDateTimeSerializer;
import com.baipiao.api.events.Event;
import com.baipiao.api.venues.PointDeserializer;
import com.baipiao.api.venues.PointSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Point;

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
    @JsonDeserialize(using = PointDeserializer.class)
    @JsonSerialize(using = PointSerializer.class)
    private Point location;
    private String category;
    private String organizer;
    private Long categoryId;
    private Long organizerId;
    private Long venueId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)

    private LocalDateTime registrationDeadline;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
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
        this.location = event.getVenue().getLocation();
        this.category = event.getCategory().getName();
        this.organizer = event.getOrganizer().getName();
        this.registrationDeadline = event.getRegistrationDeadline();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.categoryId = event.getCategory().getId();
        this.organizerId = event.getOrganizer().getId();
        this.venueId = event.getVenue().getId();
    }

    
}