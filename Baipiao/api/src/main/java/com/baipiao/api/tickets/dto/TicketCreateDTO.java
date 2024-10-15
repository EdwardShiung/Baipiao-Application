package com.baipiao.api.tickets.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.tickets.Ticket;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class TicketCreateDTO implements Serializable {
    private Long event;

    private Long user;

    private String ticketNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registrationDate;
    private String status;



    public TicketCreateDTO() {
    }

    public TicketCreateDTO(Ticket ticket) {
        this.registrationDate = ticket.getRegistrationDate();
        this.status = ticket.getStatus();
        this.ticketNo = ticket.getTicketNo();
        this.event = ticket.getEvent() != null ? ticket.getEvent().getId() : null;
        this.user = ticket.getUser() != null ? ticket.getUser().getId() : null;
    }
}