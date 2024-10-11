package com.baipiao.api.venues;

public class VenueNotFoundException extends RuntimeException{
    public VenueNotFoundException(Long id){
        super("Organizer with id " + id + " not found");
    }
}
