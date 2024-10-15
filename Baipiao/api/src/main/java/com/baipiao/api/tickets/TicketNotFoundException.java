package com.baipiao.api.tickets;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(Long eventId, Long userId) {
        super("Ticket with event id " + eventId + " and user id " + userId + " not found");
    }
}
