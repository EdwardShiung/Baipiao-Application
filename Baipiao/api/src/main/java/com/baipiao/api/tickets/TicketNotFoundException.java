package com.baipiao.api.tickets;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(Long id){
        super("Ticket with id " + id + " not found");
    }
}
