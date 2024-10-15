package com.baipiao.api.venues;

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
        Venue venue = new Venue();
        venue.setName(newVenue.getName());
        venue.setDescription(newVenue.getDescription());
        venue.setLocation(newVenue.getLocation());
        venue.setCreatedAt(newVenue.getCreatedAt());
        venue.setUpdatedAt(newVenue.getUpdatedAt());
        venueRepository.insertVenue(newVenue.getName(), newVenue.getDescription(), newVenue.getLocation(), newVenue.getCreatedBy(), newVenue.getCreatedAt(), newVenue.getUpdatedBy(), newVenue.getUpdatedAt());
        
    }

    public void update(VenueCreateDTO venue, Long id) {
        Venue existingVenue = venueRepository.findById(id).orElseThrow(() -> new VenueNotFoundException(id));
        existingVenue.setName(venue.getName());
        existingVenue.setDescription(venue.getDescription());
        existingVenue.setLocation(venue.getLocation());
        existingVenue.setUpdatedAt(venue.getUpdatedAt());
        existingVenue.setCreatedAt(venue.getCreatedAt());
        venueRepository.updateVenue(existingVenue.getId(), existingVenue.getName(), existingVenue.getDescription(), existingVenue.getLocation(), venue.getCreatedBy(), existingVenue.getCreatedAt(), venue.getUpdatedBy(), existingVenue.getUpdatedAt());
    }
}
