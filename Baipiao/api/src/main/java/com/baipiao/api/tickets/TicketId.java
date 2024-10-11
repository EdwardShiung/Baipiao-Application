package com.baipiao.api.tickets;

import java.io.Serializable;
import java.util.Objects;

public class TicketId implements Serializable {
    
    private Long event;
    private Long user;

    // Default constructor
    public TicketId() {}

    // Parameterized constructor
    public TicketId(Long event, Long user) {
        this.event = event;
        this.user = user;
    }

    // Getters and setters
    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketId that = (TicketId) o;
        return Objects.equals(event, that.event) &&
               Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}