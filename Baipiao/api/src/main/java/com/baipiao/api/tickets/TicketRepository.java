package com.baipiao.api.tickets;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT * FROM tickets WHERE event_id = :event_id", nativeQuery = true)
    List<Ticket> findByEventId(@Param("event_id") Long event_id);
    @Query(value = "SELECT * FROM tickets WHERE event_id = :event_id AND user_id = :user_id", nativeQuery = true)
    Ticket findByEventIdAndUserId(@Param("event_id") Long event_id, @Param("user_id") Long user_id);

    @Query(value = "INSERT INTO tickets (event_id, user_id, ticket_no, status) VALUES (:event_id, :user_id, :ticket_no, :status)", nativeQuery = true)
    void createTicket(@Param("event_id") Long event_id, @Param("user_id") Long user_id, @Param("ticket_no") String ticket_no, @Param("status") String status);

    @Query(value = "UPDATE tickets SET status = :status WHERE event_id = :event_id AND user_id = :user_id", nativeQuery = true)
    void updateTicket(@Param("event_id") Long event_id, @Param("user_id") Long user_id, @Param("status") String status);

    @Query(value = "DELETE FROM tickets WHERE event_id = :event_id AND user_id = :user_id", nativeQuery = true)
    void deleteTicket(@Param("event_id") Long event_id, @Param("user_id") Long user_id);

    @Query(value = "SELECT * FROM tickets WHERE user_id = :user_id AND event_id = :event_id", nativeQuery = true)
    Optional<Ticket> findByUserAndEvent(@Param("user_id") Long userId, @Param("event_id") Long eventId);
}