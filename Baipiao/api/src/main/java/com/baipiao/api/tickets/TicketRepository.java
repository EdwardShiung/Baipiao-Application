package com.baipiao.api.tickets;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT * FROM tickets", nativeQuery = true)
    List<Ticket> findAll();

    @Query(value = "SELECT * FROM tickets WHERE event_id = :event_id", nativeQuery = true)
    List<Ticket> findByEventId(@Param("event_id") Long event_id);
    @Query(value = "SELECT * FROM tickets WHERE user_id = :user_id", nativeQuery = true)
   List<Ticket> findByUserId(@Param("user_id") Long user_id);

   @Modifying
   @Transactional
   @Query(value = "INSERT INTO tickets (event_id, user_id, ticket_no, registration_date, status) VALUES (:event_id, :user_id, :ticket_no, :registration_date, :status)", nativeQuery = true)
    void insertTicket(@Param("event_id") Long event_id, @Param("user_id") Long user_id, @Param("registration_date") LocalDateTime registrationDate, @Param("ticket_no") String ticket_no, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tickets SET status = :status, registration_date = :registration_date, ticket_no = :ticket_no WHERE event_id = :event_id AND user_id = :user_id", nativeQuery = true)
    void updateTicket(@Param("event_id") Long event_id, @Param("user_id") Long user_id, @Param("registration_date") LocalDateTime registrationDate, @Param("ticket_no") String ticket_no, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tickets WHERE event_id = :event_id AND user_id = :user_id", nativeQuery = true)
    void deleteTicket(@Param("event_id") Long event_id, @Param("user_id") Long user_id);

    @Query(value = "SELECT * FROM tickets WHERE user_id = :user_id AND event_id = :event_id", nativeQuery = true)
    Optional<Ticket> findByEventAndUser( @Param("event_id") Long eventId, @Param("user_id") Long userId);
}