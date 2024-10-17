package com.baipiao.api.venues;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.venues.VenueRepository;
import com.baipiao.api.venues.dto.VenueCreateDTO;
import com.baipiao.api.venues.dto.VenueDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    public VenueService(VenueRepository venueRepository, UserRepository userRepository) {
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }
    public List<VenueDTO> getAll() {
            return venueRepository.findAllVenues().stream().map(venue -> {
                System.out.println(venue);
                return new VenueDTO(venue);
            }).collect(Collectors.toList());
    }

    public VenueDTO find(Long id) {
        return venueRepository.findById(id).map(venue -> {
            return new VenueDTO(venue);
        }).orElse(null);
    }

    public void deleteById(Long id) {
        Venue existingVenue = venueRepository.findById(id).orElseThrow(() -> new VenueNotFoundException(id));
        venueRepository.deleteVenueById(id);
    }

    public void save(VenueCreateDTO newVenue) {  
        venueRepository.insertVenue(newVenue.getName(), newVenue.getDescription(), newVenue.getLocation(), null, LocalDateTime.now(), null, LocalDateTime.now());
        
    }

    public void update(VenueCreateDTO venue, Long id) {
        Venue existingVenue = venueRepository.findById(id).orElseThrow(() -> new VenueNotFoundException(id));
        venueRepository.updateVenue(existingVenue.getId(), venue.getName(), venue.getDescription(), venue.getLocation(),   null, LocalDateTime.now());
    }
}
