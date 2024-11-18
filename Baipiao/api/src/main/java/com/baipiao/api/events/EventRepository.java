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


    //SELECT *
    @Query(value = "SELECT events.* FROM events JOIN venues on (events.venue_id = venues.id) WHERE ST_DWithin( location::geography, ST_SetSRID(ST_MakePoint(:centerX, :centerY), 4326), :radius)", nativeQuery = true)
    List<Event> getEventsByArea(double centerX, double centerY, double radius);
    // Create: Insert a new event (not typically done in a repository, but here's a
    // native query version)
    // Create: Insert a new event
    /**
     * name, details, registrationRequired, registrationLink, contactEmail,
     * contactPhoneNumber, status;
     * private int capacity, image, venue, location, category, organizer,
     * categoryId, organizerId;
     * private Long venueId;
     * 
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO events (name, details, registration_required, registration_link, contact_email, contact_phone_number, status, capacity, image, registration_deadline, start_date, end_date, created_date, venue_id, category_id, organizer_id) " +
            "VALUES (:name, :details, :registrationRequired, :registrationLink, :contactEmail, :contactPhoneNumber, :status, :capacity, :image, :registrationDeadline, :startDate, :endDate, :createDate, :venueId, :categoryId, :organizerId)", nativeQuery = true)
    void insertEvent(
        @Param("name") String name,
        @Param("details") String details,
        @Param("registrationRequired") Boolean registrationRequired,
        @Param("registrationLink") String registrationLink,
        @Param("contactEmail") String contactEmail,
        @Param("contactPhoneNumber") String contactPhoneNumber,
        @Param("status") String status,
        @Param("capacity") Integer capacity,
        @Param("image") String image,
        @Param("registrationDeadline") LocalDateTime registrationDeadline,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("createDate") LocalDateTime createDate,
        @Param("venueId") Long venueId,
        @Param("categoryId") Long categoryId,
        @Param("organizerId") Long organizerId);

    // Update: Update a event by its ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE events SET name = :name, details = :details, registration_required = :registrationRequired, registration_link = :registrationLink, contact_email = :contactEmail, contact_phone_number = :contactPhoneNumber, status = :status, capacity = :capacity, image = :image, registration_deadline = :registrationDeadline, start_date = :startDate, end_date = :endDate, venue_id = :venueId, category_id = :categoryId, organizer_id = :organizerId WHERE id = :id", nativeQuery = true)
    void updateEvent(
        @Param("id") Long id, // The event ID to update
        @Param("name") String name,
        @Param("details") String details,
        @Param("registrationRequired") Boolean registrationRequired,
        @Param("registrationLink") String registrationLink,
        @Param("contactEmail") String contactEmail,
        @Param("contactPhoneNumber") String contactPhoneNumber,
        @Param("status") String status,
        @Param("capacity") Integer capacity,
        @Param("image") String image,
        @Param("registrationDeadline") LocalDateTime registrationDeadline,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("venueId") Long venueId,
        @Param("categoryId") Long categoryId,
        @Param("organizerId") Long organizerId
    );

    // Delete: Delete a event by its ID
    @Modifying
    @Transactional
    // @Query(value = "DELETE FROM events WHERE id = :id", nativeQuery = true)
    void deleteEventById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) > 0 FROM public.tickets t " +
               "JOIN public.users u ON t.user_id = u.id " +
               "JOIN public.events e ON t.event_id = e.id " +
               "WHERE u.username = :username AND e.id = :eventId", 
       nativeQuery = true)
    boolean isRegistered(@Param("eventId") Long eventId, @Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.tickets (event_id, user_id) VALUES (:eventId, :username)", nativeQuery = true)
    void register(@Param("eventId") Long eventId, @Param("username") String username);
}