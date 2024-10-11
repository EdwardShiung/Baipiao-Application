package com.baipiao.api.events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    @Query(value = "SELECT * FROM events", nativeQuery = true)
    List<Event> findAll();

    // Example for filtering by status
    @Query(value = "SELECT * FROM events WHERE status = :status", nativeQuery = true)
    List<Event> findByStatus(@Param("status") String status);

    // Example for filtering by capacity
    @Query(value = "SELECT * FROM events WHERE capacity >= :capacity", nativeQuery = true)
    List<Event> findByCapacityGreaterThanEqual(@Param("capacity") int capacity);

    // Example for filtering by registration required
    @Query(value = "SELECT * FROM events WHERE registration_required = :registrationRequired", nativeQuery = true)
    List<Event> findByRegistrationRequired(@Param("registrationRequired") boolean registrationRequired);
}