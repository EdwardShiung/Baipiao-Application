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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VenueCreateDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    @JsonDeserialize(using = PointDeserializer.class)
    @JsonSerialize(using = PointSerializer.class)
    private Point location;

    public VenueCreateDTO() {
    }

    public VenueCreateDTO(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.description = venue.getDescription();
        this.location = venue.getLocation();
    }
}