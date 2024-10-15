package com.baipiao.api.venues;

import java.util.List;
import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Read: Retrieve all venues
    @Query(value = "SELECT * FROM venues", nativeQuery = true)
    List<Venue> findAllVenues();

    // Read: Retrieve a venue by its ID
    @Query(value = "SELECT * FROM venues WHERE id = :id", nativeQuery = true)
    Venue findVenueById(@Param("id") Long id);

    // Read: Retrieve a venue by its name
    @Query(value = "SELECT * FROM venues WHERE name = :name", nativeQuery = true)
    Venue findVenueByName(@Param("name") String name);

    // Create: Insert a new venue (not typically done in a repository, but here's a
    // native query version)
    // Create: Insert a new venue
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO venues (name, description, location, created_by_id, created_at, updated_by_id, updated_at) VALUES (:name, :description, :location, :createdBy, :createdAt, :updatedBy, :updatedAt)", nativeQuery = true)
    void insertVenue(@Param("name") String name, @Param("description") String description, @Param("location") Point location, @Param("createdBy") Long createdBy, @Param("createdAt") LocalDateTime createdAt, @Param("updatedBy") Long updatedBy, @Param("updatedAt") LocalDateTime updatedAt);

    // Update: Update a venue by its ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE venues SET name = :name, description = :description , location = :location, created_by_id = :createdBy, created_at = :createdAt, updated_by_id = :updatedBy, updated_at = :updatedAt WHERE id = :id", nativeQuery = true)
    void updateVenue(@Param("id") Long id, @Param("name") String name, @Param("description") String description,
            @Param("location") Point location, @Param("createdBy") Long createdBy,
            @Param("createdAt") LocalDateTime createdAt, @Param("updatedBy") Long updatedBy,
            @Param("updatedAt") LocalDateTime updatedAt);

    // Delete: Delete a venue by its ID
    @Modifying
    @Transactional
    // @Query(value = "DELETE FROM venues WHERE id = :id", nativeQuery = true)
    void deleteVenueById(@Param("id") Long id);

}