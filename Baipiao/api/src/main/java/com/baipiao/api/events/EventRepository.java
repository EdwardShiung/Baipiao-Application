package com.baipiao.api.events;

import java.util.List;
import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Read: Retrieve all events
    @Query(value = "SELECT * FROM events", nativeQuery = true)
    List<Event> findAllEvents();

    // Read: Retrieve a event by its ID
    @Query(value = "SELECT * FROM events WHERE id = :id", nativeQuery = true)
    Event findEventById(@Param("id") Long id);

    // Read: Retrieve a event by its name
    @Query(value = "SELECT * FROM events WHERE eventname = :eventname", nativeQuery = true)
    Event findEventByEventName(@Param("eventname") String eventName);

    // Create: Insert a new event (not typically done in a repository, but here's a
    // native query version)
    // Create: Insert a new event
    @Modifying
    @Transactional

    @Query(value = "INSERT INTO events (email, eventname, password, phone_number, display_name, event_type, created_at) VALUES (:email, :eventname, :password, :phone_number, :display_name, :event_type, :created_at)", nativeQuery = true)
    void insertEvent(@Param("email") String name, @Param("eventname") String description, @Param("password") String password, @Param("phone_number") String phone_number, @Param("display_name") String displayName,  @Param("event_type") String eventType, @Param("created_at") LocalDateTime createdAt);

    // Update: Update a event by its ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE events SET email = :email, eventname = :eventname , display_name = :display_name, password = :password, phone_number = :phone_number, event_type= :event_type, created_at = :created_at WHERE id = :id", nativeQuery = true)
    void updateEvent(@Param("id") Long id, @Param("email") String name, @Param("eventname") String description, @Param("password") String password, @Param("phone_number") String phone_number, @Param("display_name") String displayName,  @Param("event_type") String eventType, @Param("created_at") LocalDateTime createdAt);

    // Delete: Delete a event by its ID
    @Modifying
    @Transactional
    // @Query(value = "DELETE FROM events WHERE id = :id", nativeQuery = true)
    void deleteEventById(@Param("id") Long id);

}