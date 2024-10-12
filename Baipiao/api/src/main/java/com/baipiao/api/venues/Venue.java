package com.baipiao.api.venues;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import com.baipiao.api.users.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use IDENTITY to match auto-increment behavior in SQL
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    private String description;

    // PostGIS point attribute for location (latitude, longitude)
    @JsonSerialize(using = PointSerializer.class)  // Custom serializer
    @Column(columnDefinition = "geometry(Point,4326)")  // WGS 84 (SRID 4326)
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User updatedBy;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    // Constructor for creating a Venue instance
    public Venue(String name, String description, Point location) {
        this.name = name;
        this.description = description;
        this.location = location;  // PostGIS location
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
}