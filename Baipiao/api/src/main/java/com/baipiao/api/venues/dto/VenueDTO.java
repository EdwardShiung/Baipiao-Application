package com.baipiao.api.venues.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.venues.PointDeserializer;
import com.baipiao.api.venues.PointSerializer;
import com.baipiao.api.venues.Venue;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class VenueDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    @JsonDeserialize(using = PointDeserializer.class)
    @JsonSerialize(using = PointSerializer.class)
    private Point location;

    private String createdBy;

    private String updatedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public VenueDTO() {
    }

    public VenueDTO(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.description = venue.getDescription();
        this.location = venue.getLocation();
        this.createdBy = venue.getCreatedBy() != null ? venue.getCreatedBy().getDisplayName() : "";
        this.updatedBy = venue.getUpdatedBy() != null ? venue.getUpdatedBy().getDisplayName() : "";
        this.createdAt = venue.getCreatedAt();
        this.updatedAt = venue.getUpdatedAt();
    }
}