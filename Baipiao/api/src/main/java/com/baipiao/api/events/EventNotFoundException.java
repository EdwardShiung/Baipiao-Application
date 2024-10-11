package com.baipiao.api.events;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Long id){
        super("Event with id " + id + " not found");
    }
}
