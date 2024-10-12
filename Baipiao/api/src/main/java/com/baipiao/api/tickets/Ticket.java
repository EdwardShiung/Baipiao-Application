package com.baipiao.api.tickets;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.events.Event;
import com.baipiao.api.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tickets")
@IdClass(TicketId.class)
public class Ticket implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String ticketNo;
    private LocalDateTime registrationDate;
    private String status;

    public Ticket(Event event, User user, String ticketNo, LocalDateTime registrationDate, String status) {
        this.event = event;
        this.user = user;
        this.ticketNo = ticketNo;
        this.registrationDate = registrationDate;
        this.status = status;
    }

}