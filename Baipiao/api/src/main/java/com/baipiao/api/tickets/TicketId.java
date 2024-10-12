package com.baipiao.api.tickets;

import java.io.Serializable;
import java.util.Objects;

public class TicketId implements Serializable {
    private Long event; // Must match the type of `event_id` in `Ticket`
    private Long user;  // Must match the type of `user_id` in `Ticket`

    public TicketId() {}

    public TicketId(Long event, Long user) {
        this.event = event;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketId ticketId = (TicketId) o;
        return Objects.equals(event, ticketId.event) &&
               Objects.equals(user, ticketId.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}