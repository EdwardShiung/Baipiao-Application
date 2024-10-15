package com.baipiao.api.tickets.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.events.Event;
import com.baipiao.api.tickets.Ticket;
import com.baipiao.api.users.User;

import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TicketDTO implements Serializable {

    private String event;

    private String user;

    private String ticketNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registrationDate;
    private String status;


    public TicketDTO() {
    }

    public TicketDTO(Ticket ticket) {
        this.event = ticket.getEvent().getName();
        this.user = ticket.getUser().getDisplayName();
        this.ticketNo = ticket.getTicketNo();
        this.registrationDate = ticket.getRegistrationDate();
        this.status = ticket.getStatus();
    }
}